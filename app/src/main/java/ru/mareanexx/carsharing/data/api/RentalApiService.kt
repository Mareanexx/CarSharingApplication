package ru.mareanexx.carsharing.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mareanexx.carsharing.data.models.RentalHistory

interface RentalApiService {
    @GET("rental/history")
    suspend fun getCompletedRentals(@Query("idUser") idUser: Int): Response<List<RentalHistory>>
}
