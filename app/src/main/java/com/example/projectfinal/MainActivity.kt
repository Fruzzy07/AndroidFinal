package com.example.projectfinal

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.Calendar



class MainActivity : AppCompatActivity() {
    private lateinit var textViewCountry: TextView
    private lateinit var textViewGender: TextView
    private lateinit var textViewDOB: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        textViewCountry = findViewById(R.id.textview_country)
        textViewGender = findViewById(R.id.textview_gender)
        textViewDOB = findViewById(R.id.textview_dob)

        val myToolbar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(myToolbar)

        val myDrawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)



        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Обработка выбора элемента меню
            when (menuItem.itemId) {
                R.id.profile_button -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    myDrawerLayout.close()
                    true
                }
                R.id.month_gone_button -> {
                    val intent = Intent(this, OtherActivity::class.java)
                    startActivity(intent)
                    myDrawerLayout.close()
                    true
                }
                // Добавьте обработку других пунктов меню при необходимости
                else -> false
            }
        }

        val myToogle = ActionBarDrawerToggle(this, myDrawerLayout, myToolbar, R.string.drawer_open, R.string.drawer_close)

        myDrawerLayout.addDrawerListener(myToogle)

        myToogle.syncState()


        // Find views
        val containerDOB = findViewById<LinearLayout>(R.id.container_dob)
        val containerCountry = findViewById<LinearLayout>(R.id.container_country)
        val containerGender = findViewById<LinearLayout>(R.id.container_gender)


        textViewCountry.text = sharedPreferences.getString("country", "")
        textViewGender.text = sharedPreferences.getString("gender", "")
        textViewDOB.text = sharedPreferences.getString("dob", "")

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
                    val formattedDate =
                        "%02d/%02d/%d".format(selectedDay, selectedMonth + 1, selectedYear)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_btn -> {
                // Get the text from TextViews
                val dob = textViewDOB.text.toString()
                val country = textViewCountry.text.toString()
                val gender = textViewGender.text.toString()

                // Save the data using SharedPreferences
                with(sharedPreferences.edit()) {
                    putString("dob", dob)
                    putString("country", country)
                    putString("gender", gender)
                    apply()
                }

                // Show a toast message to indicate that the data has been saved
                Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}