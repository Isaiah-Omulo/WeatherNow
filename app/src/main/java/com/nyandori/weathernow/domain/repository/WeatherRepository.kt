package com.nyandori.weathernow.domain.repository

import com.nyandori.weathernow.domain.model.Weather
import com.nyandori.weathernow.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    /**
     * Gets the weather data for a given location.
     * This function implements the offline-first strategy. It will:
     * 1. Immediately emit the cached data from the local database (if available).
     * 2. Trigger a network request to fetch fresh data.
     * 3. If the network request is successful, it updates the database, and the Flow will
     *    automatically emit the new data.
     * 4. If the network request fails, it will emit an Error state, but the previously
     *    emitted cached data will still be available to the UI.
     *
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @return A Flow of Resource-wrapped Weather data.
     */
    fun getWeather(lat: Double, lon: Double): Flow<Resource<Weather>>
}