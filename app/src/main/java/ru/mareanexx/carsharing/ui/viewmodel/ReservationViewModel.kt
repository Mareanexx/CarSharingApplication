package ru.mareanexx.carsharing.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mareanexx.carsharing.data.api.RentalApiService
import ru.mareanexx.carsharing.data.models.NewReservationRequest
import ru.mareanexx.carsharing.network.RetrofitClient

class ReservationViewModel : ViewModel() {
    private val reservationApiService : RentalApiService = RetrofitClient.retrofitCarsh.create(RentalApiService::class.java)

    fun makeReservation(
        newReservation: NewReservationRequest,
        onSuccess: () -> Unit,
        onError: () -> Unit = { }
    ) {
        viewModelScope.launch {
            try {
                val response = reservationApiService.createNewReservation(newReservation)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("RESERVATION", "Успешно создано бронирование, теперь перенаправляю на ориг экран")
                        onSuccess() // перенаправление на другой экран home_map
                    }
                } else {
                    onError()
                }
            } catch (e: Exception) {
                Log.e("RESERVATION", "${e.message}")
                onError()
            }
        }
    }
}