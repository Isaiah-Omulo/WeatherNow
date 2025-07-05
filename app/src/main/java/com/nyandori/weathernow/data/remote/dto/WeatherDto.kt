package com.nyandori.weathernow.data.remote.dto

import com.google.gson.annotations.SerializedName

// Using @SerializedName makes our code resilient to API changes
// and allows us to use more idiomatic Kotlin property names.
data class WeatherDto(
    @SerializedName("coord")
    val coordinates: Coordinates,
    @SerializedName("weather")
    val weatherConditions: List<WeatherCondition>,
    @SerializedName("main")
    val main: Main,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("dt")
    val timestamp: Long,
    @SerializedName("name")
    val cityName: String
)

data class Coordinates(
    val lon: Double,
    val lat: Double
)

data class WeatherCondition(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Int
)