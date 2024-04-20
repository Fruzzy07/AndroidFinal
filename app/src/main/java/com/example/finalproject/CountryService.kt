package com.example.finalproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CountryService {
    @Headers("X-Api-Key: Y0Oayb616M8R6GJEWIOvfA==bqYEurhfiYekaQPw")
    @GET("country")
    fun getCountries(): Call<List<Country>>
}

