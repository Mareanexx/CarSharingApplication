package ru.mareanexx.carsharing.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.mareanexx.carsharing.data.models.Location

interface LocationApiService {
    @GET("location/list")
    suspend fun getAllLocations() : Response<List<Location>>
}