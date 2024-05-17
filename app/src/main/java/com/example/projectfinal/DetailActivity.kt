package com.example.projectfinal

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var editTextNotes: EditText

    // В DetailActivity.kt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Получаем индекс месяца
        val monthIndex = intent.getIntExtra("MONTH_INDEX", -1)

        sharedPreferences = getSharedPreferences("Notes", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        editTextNotes = findViewById(R.id.edittext_notes)

        // Получаем сохраненные заметки для текущего месяца
        val savedNotes = sharedPreferences.getString("notes_$monthIndex", "")
        editTextNotes.setText(savedNotes)

        val textViewDetail = findViewById<TextView>(R.id.textview_detail)
        textViewDetail.text = "Detail for month: $monthIndex"

        findViewById<ImageButton>(R.id.button_good).setOnClickListener {
            setResultAndFinish(Activity.RESULT_OK, monthIndex, "GOOD")
        }
        findViewById<ImageButton>(R.id.button_ok).setOnClickListener {
            setResultAndFinish(Activity.RESULT_OK, monthIndex, "OK")
        }
        findViewById<ImageButton>(R.id.button_bad).setOnClickListener {
            setResultAndFinish(Activity.RESULT_OK, monthIndex, "BAD")
        }
    }

    private fun saveNotes(monthIndex: Int) {
        val notes = editTextNotes.text.toString()
        editor.putString("notes_$monthIndex", notes) // Сохраняем заметки с использованием ключа, основанного на индексе месяца
        editor.apply()
    }

    private fun setResultAndFinish(resultCode: Int, monthIndex: Int, status: String) {
        saveNotes(monthIndex) // Сохраняем заметки перед завершением активности
        val resultIntent = Intent().apply {
            putExtra("MONTH_INDEX", monthIndex)
            putExtra("STATUS", status)
        }
        setResult(resultCode, resultIntent)
    }
}