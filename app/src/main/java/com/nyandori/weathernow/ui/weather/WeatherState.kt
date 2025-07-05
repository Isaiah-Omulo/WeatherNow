package com.nyandori.weathernow.ui.weather


import com.nyandori.weathernow.domain.model.Weather

data class WeatherState(
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
