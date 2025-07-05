package com.nyandori.weathernow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nyandori.weathernow.data.local.model.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val dao: WeatherDao
}