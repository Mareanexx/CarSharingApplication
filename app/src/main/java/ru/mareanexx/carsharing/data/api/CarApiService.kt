package ru.mareanexx.carsharing.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mareanexx.carsharing.data.models.Car

interface CarApiService {
    @GET("car/loc-list")
    suspend fun getAllAvailableCarsAtLocation(@Query("idLocation") idLocation: Int) : Response<List<Car>>
}