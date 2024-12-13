package ru.mareanexx.carsharing.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mareanexx.carsharing.data.api.RentalApiService
import ru.mareanexx.carsharing.data.models.RentalHistory
import ru.mareanexx.carsharing.network.RetrofitClient

class RentalHistoryViewModel : ViewModel() {
    private val rentalApiService: RentalApiService = RetrofitClient.retrofitCarsh.create(RentalApiService::class.java)

    private val _rentalHistory = MutableStateFlow<List<RentalHistory>>(emptyList())
    val rentalHistory: StateFlow<List<RentalHistory>> = _rentalHistory

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    fun fetchRentalHistory(userId: Int, onError: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = rentalApiService.getCompletedRentals(userId)
                Log.i("RENTAL", "Trying to get Rental History of User with id = $userId")
                if (response.isSuccessful) {
                    _rentalHistory.emit(response.body() ?: emptyList())
                    Log.i("RENTAL", "Data fetched: ${_rentalHistory.value}")
                    Log.i("RENTAL", "Data from LIVE DATA: ${rentalHistory.value}")
                } else {
                    Log.e("RENTAL_ERROR", "Error: ${response.code()}")
                    onError()
                }
            } catch (e: Exception) {
                Log.e("RENTAL_ERROR", "Exception: ${e.message}")
                onError()
            } finally {
                Log.d("RENTAL", "Setting loading to false!!!")
                _loading.value = false
            }
        }
    }
}