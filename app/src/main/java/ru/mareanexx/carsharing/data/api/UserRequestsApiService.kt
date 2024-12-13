package ru.mareanexx.carsharing.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.mareanexx.carsharing.data.models.UserRequestRequest
import ru.mareanexx.carsharing.data.models.UserRequestResponse

interface UserRequestsApiService {
    @GET("list")
    suspend fun getAllUserRequests(@Query("idUser") idUser: Int) : Response<List<UserRequestResponse>>

    @POST("create")
    suspend fun createRequest(@Body userRequestRequest: UserRequestRequest) : Response<Map<String, String>>
}