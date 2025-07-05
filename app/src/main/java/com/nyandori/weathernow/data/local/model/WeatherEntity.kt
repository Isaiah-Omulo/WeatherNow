package com.nyandori.weathernow.data.local.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val cityName: String,
    val temperature: Double,
    val condition: String,
    val icon: String,
    val humidity: Int,
    val windSpeed: Double,
    val lastUpdatedTimestamp: Long
)
