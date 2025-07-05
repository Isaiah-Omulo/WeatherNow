package com.nyandori.weathernow.domain.model

data class Weather(
    val cityName: String,
    val temperature: Double,
    val condition: String,
    val conditionIconUrl: String,
    val humidity: Int,
    val windSpeed: Double,
    val lastUpdatedTimestamp: Long // Used for the "Last Updated" display
)
