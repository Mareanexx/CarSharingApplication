package ru.mareanexx.carsharing.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mareanexx.carsharing.utils.LocalDateTimeAdapter
import java.time.LocalDateTime

const val PORT_AUTH = "8081"
const val PORT_CARSH = "8082"
const val PORT_REQUESTS = "8083"

object RetrofitClient {
    // url сервера авторизации и регистрации
    private const val BASE_URL_AUTH = "http://10.0.2.2:${PORT_AUTH}/api/auth/"

    // url сервера каршеринга
    private const val BASE_URL_CARSH = "http://10.0.2.2:${PORT_CARSH}/api/"

    // url сервера пользовательских обращений
    private const val BASE_URL_REQUESTS = "http://10.0.2.2:${PORT_REQUESTS}/api/request/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_AUTH)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    val retrofitCarsh: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_CARSH)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    val retrofitRequests : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_REQUESTS)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}