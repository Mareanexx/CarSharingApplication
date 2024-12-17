package ru.mareanexx.carsharing.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mareanexx.carsharing.data.api.LocationApiService
import ru.mareanexx.carsharing.data.models.Location
import ru.mareanexx.carsharing.network.RetrofitClient

class LocationViewModel : ViewModel() {
    private val locationApiService = RetrofitClient.retrofitCarsh.create(LocationApiService::class.java)

    private val _locations = MutableStateFlow<List<Location>>(emptyList())
    val locations: StateFlow<List<Location>>
        get() = _locations

    private val _loading = MutableStateFlow(true)
    val loading : StateFlow<Boolean>
        get() = _loading

    fun getAllLocations() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = locationApiService.getAllLocations()
                Log.i("LOCATION", "Trying to get Locations")
                if (response.isSuccessful) {
                    _locations.emit(response.body() ?: emptyList())
                    Log.i("LOCATION", "Data fetched: ${_locations.value}")
                    Log.i("LOCATION", "Data from LIVE DATA: ${locations.value}")
                } else {
                    Log.e("LOCATION", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("LOCATION", "Exception: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }
}