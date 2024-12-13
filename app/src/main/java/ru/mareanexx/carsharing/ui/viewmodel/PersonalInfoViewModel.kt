package ru.mareanexx.carsharing.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mareanexx.carsharing.data.api.PersonalInfoApiService
import ru.mareanexx.carsharing.data.models.PersonalInfo
import ru.mareanexx.carsharing.network.RetrofitClient

class PersonalInfoViewModel : ViewModel() {
    private val personalInfoApiService: PersonalInfoApiService = RetrofitClient.retrofitCarsh.create(PersonalInfoApiService::class.java)

    private val _personalInfo = MutableStateFlow(PersonalInfo("", "", ""))
    val personalInfo: StateFlow<PersonalInfo>
        get() = _personalInfo

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean>
        get() = _loading

    fun fetchPersonalInfo(idUser: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = personalInfoApiService.getPersonalInfo(idUser)
                Log.i("PERS-INFO", "Trying to get Personal Info of User with id = $idUser")
                if (response.isSuccessful) {
                    _personalInfo.emit(response.body() ?: PersonalInfo("", "", ""))
                    Log.i("PERS-INFO", "Data fetched: ${_personalInfo.value}")
                    Log.i("PERS-INFO", "Data from LIVE DATA: ${personalInfo.value}")
                } else {
                    Log.e("PERS-INFO", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("PERS-INFO", "Exception: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }
}