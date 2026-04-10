package com.example.fitnesstrackerapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var steps = 6500
    private val goal = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentDate = findViewById<TextView>(R.id.currentDate)
        val stepsNumber = findViewById<TextView>(R.id.stepsNumber)
        val goalProgressBar = findViewById<ProgressBar>(R.id.goalProgressBar)
        val percentageText = findViewById<TextView>(R.id.percentageText)
        val updateStatsButton = findViewById<Button>(R.id.updateStatsButton)

        val dateFormat = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
        currentDate.text = dateFormat.format(Date())

        fun refreshDashboard() {
            stepsNumber.text = steps.toString()
            val progress = (steps.toFloat() / goal * 100).toInt()
            goalProgressBar.progress = progress
            percentageText.text = "$progress%"
            if (progress >= 100) {
                Toast.makeText(this, "🎉 Congratulations! You reached your daily goal!", Toast.LENGTH_LONG).show()
            }
        }

        refreshDashboard()

        updateStatsButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Update Step Count")
            val inputField = EditText(this)
            inputField.setText(steps.toString())
            inputField.inputType = android.text.InputType.TYPE_CLASS_NUMBER
            builder.setView(inputField)
            builder.setPositiveButton("Save") { _, _ ->
                val newValue = inputField.text.toString().toIntOrNull() ?: steps
                steps = newValue
                refreshDashboard()
            }
            builder.setNegativeButton("Cancel", null)
            builder.show()
        }
    }
}