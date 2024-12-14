package ru.mareanexx.carsharing.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.mareanexx.carsharing.data.api.AuthApiService
import ru.mareanexx.carsharing.data.models.LoginRequest
import ru.mareanexx.carsharing.data.models.UserRegistrationRequest
import ru.mareanexx.carsharing.network.RetrofitClient

class AuthViewModel : ViewModel() {
    // хранить токен здесь можно

    private val userIdValue = MutableLiveData<Int>()

    private val authApiService: AuthApiService =
        RetrofitClient.retrofit.create(AuthApiService::class.java)


    fun login(email: String, password: String, onSuccess: (String) -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = authApiService.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    response.body()?.let { jwtAuthResponse ->
                        Log.i("USER_ID", "Get user id = ${jwtAuthResponse.idUser}")
                        userIdValue.value = jwtAuthResponse.idUser
                        onSuccess(jwtAuthResponse.accessToken)
                    }
                } else {
                    onError() // Ошибка авторизации
                }
            } catch (e: HttpException) {
                Log.e("LOGIN_ERROR", "${e.message}")
                onError() // Ошибка сервера
            } catch (e: Exception) {
                Log.e("LOGIN_ERROR", "${e.message}")
                onError() // Другие ошибки
            }
        }
    }

    fun register(username: String, email: String, phoneNumber: String, password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = authApiService.register(
                    UserRegistrationRequest(
                        username = username,
                        email = email,
                        phoneNumber = phoneNumber,
                        password = password
                    )
                )
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        val message = body["message"]
                        Log.d("REGISTER_SUCCESS", message ?: "Success")
                        onSuccess()
                    }
                } else {
                    Log.e("REGISTER_ERROR", "Response Code: ${response.code()}")
                    onError() // Ошибка в запросе (ответ Bad Request)
                }
            } catch (e: HttpException) {
                Log.e("REGISTER_ERROR", "${e.message}")
                onError() // Ошибка HTTP
            } catch (e: Exception) {
                Log.e("REGISTER_ERROR", "${e.message}")
                onError() // Другие ошибки
            }
        }
    }
}