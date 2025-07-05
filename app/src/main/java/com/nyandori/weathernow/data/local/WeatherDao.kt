package com.nyandori.weathernow.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyandori.weathernow.data.local.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    // OnConflictStrategy.REPLACE ensures that if we insert a new weather item,
    // it will replace the old one, since we use a fixed primary key.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    // Using Flow ensures that the UI is automatically updated whenever the
    // data in the database changes. This is a key requirement.
    @Query("SELECT * FROM weather_table WHERE id = 1")
    fun getWeather(): Flow<WeatherEntity?>
}