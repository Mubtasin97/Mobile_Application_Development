package com.example.studentregistrationapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val StudentId = findViewById<EditText>(R.id.StudentId)
        val FullName = findViewById<EditText>(R.id.FullName)
        val Email = findViewById<EditText>(R.id.Email)
        val Password = findViewById<EditText>(R.id.Password)
        val Age = findViewById<EditText>(R.id.Age)

        val Gender = findViewById<RadioGroup>(R.id.Gender)

        val Football = findViewById<CheckBox>(R.id.Football)
        val Cricket = findViewById<CheckBox>(R.id.Cricket)
        val Basketball = findViewById<CheckBox>(R.id.Basketball)
        val Badminton = findViewById<CheckBox>(R.id.Badminton)

        val spinnerCountry = findViewById<Spinner>(R.id.spinnerCountry)
        val SelectDate = findViewById<Button>(R.id.SelectDate)
        val tSelectDate = findViewById<TextView>(R.id.tSelectDate)

        val Submit = findViewById<Button>(R.id.Submit)
        val Reset = findViewById<Button>(R.id.Reset)

        // ==================== COUNTRIES IN SPINNER ====================
        val countries = arrayOf("Bangladesh", "India", "USA", "UK", "Canada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCountry.adapter = adapter

        var selectedDate = ""
        SelectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "$d/${m+1}/$y"
                SelectDate.text = "Selected: $selectedDate"
            }, year, month, day).show()
        }

        Submit.setOnClickListener {
            val studentId = StudentId.text.toString().trim()
            val fullName = FullName.text.toString().trim()
            val email = Email.text.toString().trim()
            val password = Password.text.toString().trim()
            val ageStr = Age.text.toString().trim()

            val gender = when (Gender.checkedRadioButtonId) {
                R.id.Male -> "Male"
                R.id.Female -> "Female"
                R.id.Other -> "Other"
                else -> ""
            }


            val sportsList = mutableListOf<String>()
            if (Football.isChecked) sportsList.add("Football")
            if (Cricket.isChecked) sportsList.add("Cricket")
            if (Basketball.isChecked) sportsList.add("Basketball")
            if (Badminton.isChecked) sportsList.add("Badminton")
            val sports = if (sportsList.isEmpty()) "None" else sportsList.joinToString(", ")

            val country = spinnerCountry.selectedItem.toString()


            if (studentId.isEmpty() || fullName.isEmpty() || email.isEmpty() ||
                password.isEmpty() || ageStr.isEmpty() || gender.isEmpty() || selectedDate.isEmpty()) {
                Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val age = ageStr.toIntOrNull()
            if (age == null || age <= 0) {
                Toast.makeText(this, "Age must be greater than 0", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!email.contains("@")) {
                Toast.makeText(this, "Email must contain '@'", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            val message = """
                ID: $studentId
                Name: $fullName
                Email: $email
                Password: $password
                Age: $age
                Gender: $gender
                Sports: $sports
                Country: $country
                DOB: $selectedDate
            """.trimIndent()

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }


        Reset.setOnClickListener {
            StudentId.text.clear()
            FullName.text.clear()
            Email.text.clear()
            Password.text.clear()
            Age.text.clear()

            Gender.clearCheck()

            Football.isChecked = false
            Cricket.isChecked = false
            Basketball.isChecked = false
            Badminton.isChecked = false

            spinnerCountry.setSelection(0)

            selectedDate = ""
            tSelectDate.text = ""
        }
    }
}