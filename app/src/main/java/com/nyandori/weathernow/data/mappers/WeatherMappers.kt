package com.nyandori.weathernow.data.mappers

import com.nyandori.weathernow.data.local.model.WeatherEntity
import com.nyandori.weathernow.data.remote.dto.WeatherDto
import com.nyandori.weathernow.domain.model.Weather

// Mapper to convert from API DTO to Database Entity
fun WeatherDto.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        cityName = this.cityName,
        temperature = this.main.temp,
        condition = this.weatherConditions.firstOrNull()?.main ?: "Unknown",
        icon = this.weatherConditions.firstOrNull()?.icon ?: "",
        humidity = this.main.humidity,
        windSpeed = this.wind.speed,
        lastUpdatedTimestamp = System.currentTimeMillis() // Store when it was fetched
    )
}

// Mapper to convert from Database Entity to Domain Model
fun WeatherEntity.toWeather(): Weather {
    return Weather(
        cityName = this.cityName,
        temperature = this.temperature,
        condition = this.condition,
        // Construct the full URL for the icon
        conditionIconUrl = "https://openweathermap.org/img/wn/${this.icon}@2x.png",
        humidity = this.humidity,
        windSpeed = this.windSpeed,
        lastUpdatedTimestamp = this.lastUpdatedTimestamp
    )
}