package com.nyandori.weathernow.ui.weather


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyandori.weathernow.domain.location.LocationTracker
import com.nyandori.weathernow.domain.repository.WeatherRepository
import com.nyandori.weathernow.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    fun loadWeatherInfo() {
        viewModelScope.launch {
            // Set initial loading state
            _state.value = _state.value.copy(isLoading = true, error = null)

            // Fetch location
            val location = locationTracker.getCurrentLocation()
            if (location == null) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
                return@launch
            }

            // Fetch weather data from the repository
            repository.getWeather(location.latitude, location.longitude).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            weather = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            weather = result.data, // May still contain old data
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }
}