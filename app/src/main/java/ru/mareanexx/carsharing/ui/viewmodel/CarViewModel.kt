package ru.mareanexx.carsharing.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mareanexx.carsharing.data.api.CarApiService
import ru.mareanexx.carsharing.data.models.Car
import ru.mareanexx.carsharing.network.RetrofitClient

class CarViewModel : ViewModel() {
    private val carApiService = RetrofitClient.retrofitCarsh.create(CarApiService::class.java)

    private val _carsAtLocation = MutableStateFlow<List<Car>>(emptyList())
    val carsAtLocation : StateFlow<List<Car>>
        get() = _carsAtLocation

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    fun fetchAllAvailableCars(idLocation: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = carApiService.getAllAvailableCarsAtLocation(idLocation)
                Log.i("CARS", "Trying to get Cars of Location with id = $idLocation")
                if (response.isSuccessful) {
                    _carsAtLocation.emit(response.body() ?: emptyList())
                    Log.i("CARS", "Data fetched: ${_carsAtLocation.value}")
                    Log.i("CARS", "Data from LIVE DATA: ${carsAtLocation.value}")
                } else {
                    Log.e("CARS", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CARS", "Exception: ${e.message}")
            } finally {
                Log.d("CARS", "Setting loading to false!!!")
                _loading.value = false
            }
        }
    }

}