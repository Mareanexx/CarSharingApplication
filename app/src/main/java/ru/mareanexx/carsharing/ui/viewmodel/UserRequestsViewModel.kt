package ru.mareanexx.carsharing.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.mareanexx.carsharing.data.api.UserRequestsApiService
import ru.mareanexx.carsharing.data.models.UserRequestRequest
import ru.mareanexx.carsharing.data.models.UserRequestResponse
import ru.mareanexx.carsharing.network.RetrofitClient

class UserRequestsViewModel : ViewModel() {
    private val userRequestsApiService : UserRequestsApiService = RetrofitClient.retrofitRequests.create(UserRequestsApiService::class.java)

    private val _userRequests = MutableStateFlow<List<UserRequestResponse>>(emptyList())
    val userRequests: StateFlow<List<UserRequestResponse>>
        get() = _userRequests

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean>
        get() = _loading

    // получить все обращения пользователя
    fun fetchUserRequests(idUser: Int, onError: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = userRequestsApiService.getAllUserRequests(idUser)
                Log.i("REQUESTS", "Trying to get User Requests of User with id = $idUser")
                if (response.isSuccessful) {
                    _userRequests.emit(response.body() ?: emptyList())
                    Log.i("REQUESTS", "Data fetched: ${_userRequests.value}")
                    Log.i("REQUESTS", "Data from LIVE DATA: ${userRequests.value}")
                } else {
                    Log.e("REQUESTS", "Error: ${response.code()}")
                    onError()
                }
            } catch (e: Exception) {
                Log.e("REQUESTS", "Exception: ${e.message}")
                onError()
            } finally {
                Log.d("REQUESTS", "Setting loading to false!!!")
                _loading.value = false
            }
        }
    }

    // Создание нового обращения пользователя
    fun createNewRequest(
        messageTitle : String, message: String, idUser: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = userRequestsApiService.createRequest(
                    UserRequestRequest(messageTitle, message, idUser)
                )
                if (response.isSuccessful) {
                    response.body()?.let { userRequestResponse ->
                        Log.i("USER_REQUESTS", "Response: ${userRequestResponse["message"]}")
                        onSuccess()
                    }
                } else {
                    onError()
                }
            } catch (e: HttpException) {
                Log.e("USER_REQUESTS", "HttpException: ${e.message}")
                onError() // Ошибка сервера
            } catch (e: Exception) {
                Log.e("USER_REQUESTS", "Exception: ${e.message}")
                onError()
            }
        }
    }

}