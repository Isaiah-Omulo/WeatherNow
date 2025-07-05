package com.nyandori.weathernow.data.remote



import com.nyandori.weathernow.data.remote.dto.WeatherDto
import com.nyandori.weathernow.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = Constants.API_KEY,
        @Query("units") units: String = "metric" // Or "imperial" for Fahrenheit
    ): WeatherDto
}