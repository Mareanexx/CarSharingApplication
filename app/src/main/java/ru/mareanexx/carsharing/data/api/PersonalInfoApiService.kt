package ru.mareanexx.carsharing.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mareanexx.carsharing.data.models.PersonalInfo

interface PersonalInfoApiService {
    @GET("pers-info")
    suspend fun getPersonalInfo(@Query("idUser") idUser: Int) : Response<PersonalInfo?>
}