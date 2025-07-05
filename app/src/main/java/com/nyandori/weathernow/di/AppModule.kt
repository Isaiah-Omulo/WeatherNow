package com.nyandori.weathernow.di

import android.app.Application
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nyandori.weathernow.data.local.WeatherDatabase
import com.nyandori.weathernow.data.remote.OpenWeatherApiService
import com.nyandori.weathernow.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides a singleton instance of OkHttpClient.
     * Includes a logging interceptor to log network requests and responses in debug builds,
     * which is crucial for development and debugging.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    /**
     * Provides a singleton instance of the Retrofit client for network operations.
     */
    @Provides
    @Singleton
    fun provideOpenWeatherApi(okHttpClient: OkHttpClient): OpenWeatherApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApiService::class.java)
    }

    /**
     * Provides a singleton instance of the Room database.
     */
    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application): WeatherDatabase {
        return Room.databaseBuilder(
            app,
            WeatherDatabase::class.java,
            "weather.db"
        ).build()
    }

    /**
     * Provides a singleton instance of the FusedLocationProviderClient for getting the device location.
     */
    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }
}