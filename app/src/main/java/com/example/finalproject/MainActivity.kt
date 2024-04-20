package com.example.finalproject

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var textViewCountry: TextView
    private lateinit var textViewGender: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewCountry = findViewById(R.id.textview_country)
        textViewGender = findViewById(R.id.textview_gender)

        // Find views
        val containerDOB = findViewById<RelativeLayout>(R.id.container_dob)
        val containerCountry = findViewById<RelativeLayout>(R.id.container_country)
        val containerGender = findViewById<RelativeLayout>(R.id.container_gender)

        // Set click listener for Date of Birth container
        containerDOB.setOnClickListener {
            // Get current date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Create a DatePickerDialog and show it
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Handle selected date
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    // Do something with the selected date
                    val formattedDate = "%02d/%02d/%d".format(selectedDay, selectedMonth + 1, selectedYear)
                    // For example, you can update a TextView with the selected date
                    val textViewDOB = findViewById<TextView>(R.id.textview_dob)
                    textViewDOB.text = formattedDate
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        containerCountry.setOnClickListener {
            fetchCountries()
        }

        containerGender.setOnClickListener {
            // Display a dialog with two options: Male and Female
            val genderOptions = arrayOf("Male", "Female")
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Select Gender")
                .setItems(genderOptions) { _, which ->
                    // Handle the selected gender
                    val selectedGender = genderOptions[which]
                    // Update the TextView or perform any other action based on the selected gender
                    textViewGender.text = selectedGender
                }
            dialogBuilder.create().show()
        }
    }

    private fun fetchCountries() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-ninjas.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CountryService::class.java)
        val call = service.getCountries()

        call.enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful) {
                    val countries = response.body()
                    updateCountriesRecyclerView(countries ?: emptyList()) // Pass countries to updateCountriesRecyclerView
                } else {
                    textViewCountry.text = "Failed to fetch countries: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                textViewCountry.text = "Failed to fetch countries: ${t.message}"
            }
        })
    }

    // Function to update RecyclerView with fetched countries
    private fun updateCountriesRecyclerView(countries: List<Country>) {
        val recyclerView = findViewById<RecyclerView>(R.id.countries_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CountryAdapter(countries)
    }
}