package com.example.finalproject

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

        containerCountry.setOnClickListener {
            val countries = resources.getStringArray(R.array.country_names)
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Choose a Country")
                .setItems(countries) { _, which ->
                    // Handle click here. which is the index of the selected item.
                    val selectedCountry = countries[which]
                    textViewCountry.text = selectedCountry
                }
            dialogBuilder.create().show()
        }

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

}