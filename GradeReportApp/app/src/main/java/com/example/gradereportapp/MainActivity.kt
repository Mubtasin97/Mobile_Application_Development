package com.example.gradereportapp

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var gradeTable: TableLayout
    private lateinit var gpaValueText: TextView
    private lateinit var subjectNameInput: EditText
    private lateinit var marksObtainedInput: EditText
    private lateinit var marksTotalInput: EditText
    private lateinit var addSubjectButton: Button
    private lateinit var printShareButton: Button

    private val subjects = mutableListOf(
        Subject("Computer Network", 85, 100),
        Subject("Introduction to Programming", 78, 100),
        Subject("Chemistry", 92, 100),
        Subject("Programming in Python", 65, 100),
        Subject("Machine Learning", 45, 100),
        Subject("Mobile Application Development", 88, 100)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gradeTable = findViewById(R.id.gradeTable)
        gpaValueText = findViewById(R.id.gpaValueText)
        subjectNameInput = findViewById(R.id.subjectNameInput)
        marksObtainedInput = findViewById(R.id.marksObtainedInput)
        marksTotalInput = findViewById(R.id.marksTotalInput)
        addSubjectButton = findViewById(R.id.addSubjectButton)
        printShareButton = findViewById(R.id.printShareButton)

        addSubjectButton.setOnClickListener { addNewSubject() }
        printShareButton.setOnClickListener {
            Toast.makeText(this, "Grade report ready to print/share", Toast.LENGTH_LONG).show()
        }

        buildGradeTable()
    }

    private fun addNewSubject() {
        val subjectName = subjectNameInput.text.toString().trim()
        val obtainedStr = marksObtainedInput.text.toString().trim()
        val totalStr = marksTotalInput.text.toString().trim()

        if (subjectName.isEmpty() || obtainedStr.isEmpty() || totalStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val obtained = obtainedStr.toIntOrNull() ?: 0
        val total = totalStr.toIntOrNull() ?: 100

        if (obtained > total || obtained < 0 || total <= 0) {
            Toast.makeText(this, "Invalid marks", Toast.LENGTH_SHORT).show()
            return
        }

        subjects.add(Subject(subjectName, obtained, total))
        buildGradeTable()

        subjectNameInput.text.clear()
        marksObtainedInput.text.clear()
        marksTotalInput.text.clear()
    }

    private fun buildGradeTable() {
        gradeTable.removeAllViews()

        val headerRow = TableRow(this)
        headerRow.setBackgroundColor(Color.parseColor("#1976D2"))
        addHeaderCell(headerRow, "Subject")
        addHeaderCell(headerRow, "Obtained")
        addHeaderCell(headerRow, "Total")
        addHeaderCell(headerRow, "Grade")
        gradeTable.addView(headerRow)

        var rowIndex = 0
        for (subject in subjects) {
            val row = TableRow(this)
            row.setPadding(8, 16, 8, 16)
            val isFail = subject.grade == "F"
            row.setBackgroundColor(if (isFail) Color.parseColor("#FFCDD2") else Color.parseColor("#C8E6C9"))

            addCell(row, subject.name)
            addCell(row, subject.obtained.toString())
            addCell(row, subject.total.toString())
            addCell(row, subject.grade)

            gradeTable.addView(row)
            rowIndex++
        }

        val summaryRow = TableRow(this)
        summaryRow.setPadding(8, 16, 8, 16)
        val summaryText = TextView(this)
        summaryText.text = "Total Subjects: ${subjects.size}   Passed: ${subjects.count { it.grade != "F" }}   Failed: ${subjects.count { it.grade == "F" }}"
        summaryText.setPadding(16, 12, 16, 12)
        summaryText.textSize = 16f
        summaryText.setBackgroundColor(Color.parseColor("#E3F2FD"))
        val params = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        params.span = 4
        summaryText.layoutParams = params
        summaryRow.addView(summaryText)
        gradeTable.addView(summaryRow)

        updateGPA()
    }

    private fun addHeaderCell(row: TableRow, text: String) {
        val cell = TextView(this)
        cell.text = text
        cell.setPadding(16, 16, 16, 16)
        cell.textSize = 16f
        cell.setTextColor(Color.WHITE)
        cell.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        row.addView(cell)
    }

    private fun addCell(row: TableRow, text: String) {
        val cell = TextView(this)
        cell.text = text
        cell.setPadding(16, 12, 16, 12)
        cell.textSize = 15f
        cell.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        row.addView(cell)
    }

    private fun updateGPA() {
        val totalGpaPoints = subjects.sumOf { it.gpaPoint }
        val averageGpa = if (subjects.isNotEmpty()) totalGpaPoints / subjects.size else 0.0
        gpaValueText.text = String.format(Locale.getDefault(), "GPA: %.2f", averageGpa)
    }
}

data class Subject(val name: String, val obtained: Int, val total: Int) {
    val grade: String
        get() {
            val percentage = (obtained.toFloat() / total * 100).toInt()
            return when {
                percentage >= 90 -> "A+"
                percentage >= 80 -> "A"
                percentage >= 70 -> "B+"
                percentage >= 60 -> "B"
                percentage >= 50 -> "C"
                percentage >= 40 -> "D"
                else -> "F"
            }
        }
    val gpaPoint: Double
        get() {
            return when (grade) {
                "A+" -> 4.0
                "A" -> 3.7
                "B+" -> 3.3
                "B" -> 3.0
                "C" -> 2.0
                "D" -> 1.0
                else -> 0.0
            }
        }
}