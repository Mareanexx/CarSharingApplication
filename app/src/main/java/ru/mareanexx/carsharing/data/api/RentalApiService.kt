package ru.mareanexx.carsharing.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import ru.mareanexx.carsharing.data.models.ClosedRentalRequest
import ru.mareanexx.carsharing.data.models.CompletedRentalRequest
import ru.mareanexx.carsharing.data.models.NewRentalRequest
import ru.mareanexx.carsharing.data.models.NewRentalResponse
import ru.mareanexx.carsharing.data.models.NewReservationRequest
import ru.mareanexx.carsharing.data.models.Rental
import ru.mareanexx.carsharing.data.models.RentalHistory

interface RentalApiService {
    @GET("rental/history")
    suspend fun getCompletedRentals(@Query("idUser") idUser: Int): Response<List<RentalHistory>>

    // Создать новое бронирование - на кнопку в CarAtLocationScreen
    @POST("rental/new-res")
    suspend fun createNewReservation(@Body newReservationRequest: NewReservationRequest) : Response<Map<String, String>>

    // Получить активное бронирование - только idRental передается
    @GET("rental/active")
    suspend fun getActiveRental(@Query("idUser") idUser: Int) : Response<Rental?>

    // Создать новую АРЕНДУ - только idRental передается
    @PATCH("rental/new-rent")
    suspend fun createNewRent(@Body newRentalRequest: NewRentalRequest) : Response<NewRentalResponse>

    // Закрыть бронирование, было бронирование но аренды не было
    @PATCH("rental/active/close")
    suspend fun cancelActiveRental(@Body closedRentalRequest: ClosedRentalRequest) : Response<Map<String, String>>

    // Завершить текущую аренду
    @PUT("rental/active/complete")
    suspend fun completeActiveRental(@Body completedRental: CompletedRentalRequest) : Response<Map<String, String>>
}
