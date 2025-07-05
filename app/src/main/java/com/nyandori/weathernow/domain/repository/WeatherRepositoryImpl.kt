package com.nyandori.weathernow.domain.repository


import com.nyandori.weathernow.data.local.WeatherDatabase
import com.nyandori.weathernow.data.mappers.toWeather
import com.nyandori.weathernow.data.mappers.toWeatherEntity
import com.nyandori.weathernow.data.remote.OpenWeatherApiService
import com.nyandori.weathernow.domain.model.Weather
import com.nyandori.weathernow.domain.repository.WeatherRepository
import com.nyandori.weathernow.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApiService,
    private val db: WeatherDatabase // Injecting the whole DB to access the DAO
) : WeatherRepository {

    private val dao = db.dao

    override fun getWeather(lat: Double, lon: Double): Flow<Resource<Weather>> {
        return flow {
            // 1. Start with loading state
            emit(Resource.Loading(true))

            // 2. Get cached data from the database
            val localWeather = dao.getWeather().firstOrNull() // .firstOrNull() is not a Flow operator here, it's a suspending function on Flow
            if (localWeather != null) {
                emit(Resource.Success(data = localWeather.toWeather()))
            }

            // 3. Make the network request
            try {
                val remoteWeatherDto = api.getWeather(lat, lon)

                // 4. Clear old data and insert new data into the database
                dao.insertWeather(remoteWeatherDto.toWeatherEntity())

                // 5. Emit the new data from the database (Single Source of Truth)
                val newWeather = dao.getWeather().firstOrNull()
                if (newWeather != null) {
                    emit(Resource.Success(data = newWeather.toWeather()))
                } else {
                    // This case is unlikely but handled for completeness
                    emit(Resource.Error("Unknown error: Data not found after fetch."))
                }

            } catch (e: HttpException) {
                // Handle non-2xx server responses
                emit(Resource.Error(
                    message = "API Error: ${e.message()}",
                    data = localWeather?.toWeather() // Still provide old data on error
                ))
            } catch (e: IOException) {
                // Handle network connection issues (device offline, etc.)
                emit(Resource.Error(
                    message = "Network Error: Could not reach server. Displaying cached data.",
                    data = localWeather?.toWeather() // Still provide old data on error
                ))
            } finally {
                // 6. Stop loading
                emit(Resource.Loading(false))
            }
        }
    }
}