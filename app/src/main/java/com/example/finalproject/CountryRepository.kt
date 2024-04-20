package com.example.finalproject


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryRepository {
    val api: CountryService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryService::class.java)
    }
}
