package com.example.projectfinal

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name") val name: String,
    @SerializedName("life_expectancy_male") val life_expectancy_male: Double,
    @SerializedName("life_expectancy_female") val life_expectancy_female: Double,
)
