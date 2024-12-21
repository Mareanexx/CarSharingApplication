package ru.mareanexx.carsharing.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mareanexx.carsharing.data.api.RentalApiService
import ru.mareanexx.carsharing.data.models.ClosedRentalRequest
import ru.mareanexx.carsharing.data.models.CompletedRentalRequest
import ru.mareanexx.carsharing.data.models.NewRentalRequest
import ru.mareanexx.carsharing.data.models.Rental
import ru.mareanexx.carsharing.network.RetrofitClient
import java.math.BigDecimal
import kotlin.random.Random

class RentalViewModel : ViewModel() {
    private val rentalApiService : RentalApiService = RetrofitClient.retrofitCarsh.create(RentalApiService::class.java)

    /*
        Boolean для показа нужного блока в ModalBottomSheet.
        true - показывается блок Бронирования.
        false - показывается блок Аренды.
     */
    val isRentedForBlock = MutableStateFlow(false)


    private val _activeRental = MutableStateFlow<Rental?>(null)
    val activeRental : StateFlow<Rental?>
        get() = _activeRental


    // Получить текущую аренду
    fun getActiveRental(idUser: Int) {
        viewModelScope.launch {
            try {
                val response = rentalApiService.getActiveRental(idUser)
                if (response.isSuccessful) {
                    _activeRental.emit(response.body())
                    if (_activeRental.value?.startTime != null) {
                        isRentedForBlock.value = true
                        Log.i("RENTAL", "StartTime не равно null, записываем в поле")
                    }
                    Log.i("RENTAL", "Data fetched: ${_activeRental.value}")
                    Log.i("RENTAL", "Data from LIVE DATA: ${activeRental.value}")
                } else {
                    Log.e("RENTAL", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("RENTAL", "Exception: ${e.message}")
            }
        }
    }

    // Закрыть бронирование, аренда еще не была начата
    fun closeActiveRental() {
        viewModelScope.launch {
            try {
                val response = rentalApiService.cancelActiveRental(ClosedRentalRequest(_activeRental.value!!.idRental))
                if (response.isSuccessful) {
                    _activeRental.value = null
                    isRentedForBlock.value = false
                    Log.i("RENTAL", "Данные после удаления очистки: ${_activeRental.value}")
                } else {
                    Log.e("RENTAL", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("RENTAL", "Exception: ${e.message}")
            }
        }
    }

    // Начать аренду, когда нажата "НАЧАТЬ АРЕНДУ"
    fun beginRenting(onError: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.i("NEW_RENTAL", "Пытаемся сделать запрос к серваку")
                val response = rentalApiService.createNewRent(NewRentalRequest(_activeRental.value!!.idRental))
                Log.i("NEW_RENTAL", "Запрос успех значит где то еще ошибка")
                if (response.isSuccessful) {
                    _activeRental.value?.startTime = response.body()?.startTime
                    isRentedForBlock.value = true
                    Log.i("NEW_RENTAL", "Данные были получены и записаны в startTime = ${_activeRental.value?.startTime}")
                } else {
                    Log.e("NEW_RENTAL", "Не удалось начать аренду, error: ${response.code()}")
                    onError()
                }
            } catch (e: Exception) {
                Log.e("NEW_RENTAL", "Exception: ${e.message}")
                onError()
            }
        }
    }

    fun completeActiveRental(
        totalPrice: BigDecimal,
        duration: Int,
    ) {
        viewModelScope.launch {
            try {
                Log.i("COMPLETE_RENTAL", "Пытаемся сделать запрос к серваку")
                val response = rentalApiService.completeActiveRental(
                    CompletedRentalRequest(
                        _activeRental.value!!.idRental,
                        totalPrice = totalPrice,
                        distance = Random.nextInt(5, 100),
                        duration = duration
                    )
                )
                Log.i("COMPLETE_RENTAL", "Запрос успех значит где то еще ошибка")
                if (response.isSuccessful) {
                    _activeRental.value = null
                    isRentedForBlock.value = false
                    Log.i("COMPLETE_RENTAL", "Данные после удаления очистки: ${_activeRental.value}")
                } else {
                    Log.e("COMPLETE_RENTAL", "Не удалось завершить аренду, error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("COMPLETE_RENTAL", "Exception: ${e.message}")
            }
        }
    }
}