package com.example.projectfinal

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Service {
    @Headers("X-Api-Key:Y0Oayb616M8R6GJEWIOvfA==bqYEurhfiYekaQPw")
    @GET("country")
    fun getCountryByName(@Query("name") name: String): Call<List<Country>>
}
