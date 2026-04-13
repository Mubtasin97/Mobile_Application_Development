package com.example.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val articleScrollView = findViewById<androidx.core.widget.NestedScrollView>(R.id.articleScrollView)
        val btnIntroduction = findViewById<Button>(R.id.btnIntroduction)
        val btnKeyPoints = findViewById<Button>(R.id.btnKeyPoints)
        val btnAnalysis = findViewById<Button>(R.id.btnAnalysis)
        val btnConclusion = findViewById<Button>(R.id.btnConclusion)
        val backToTopButton = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.backToTopButton)
        val bookmarkButton = findViewById<ImageButton>(R.id.bookmarkButton)
        val shareButton = findViewById<ImageButton>(R.id.shareButton)

        // Quick Navigation Buttons
        btnIntroduction.setOnClickListener {
            val section = findViewById<TextView>(R.id.sectionIntroduction)
            articleScrollView.smoothScrollTo(0, section.top)
        }
        btnKeyPoints.setOnClickListener {
            val section = findViewById<TextView>(R.id.sectionKeyPoints)
            articleScrollView.smoothScrollTo(0, section.top)
        }
        btnAnalysis.setOnClickListener {
            val section = findViewById<TextView>(R.id.sectionAnalysis)
            articleScrollView.smoothScrollTo(0, section.top)
        }
        btnConclusion.setOnClickListener {
            val section = findViewById<TextView>(R.id.sectionConclusion)
            articleScrollView.smoothScrollTo(0, section.top)
        }

        // Back to Top Button
        backToTopButton.setOnClickListener {
            articleScrollView.smoothScrollTo(0, 0)
        }

        // Bookmark Button - toggle filled/outline
        bookmarkButton.setOnClickListener {
            isBookmarked = !isBookmarked
            if (isBookmarked) {
                bookmarkButton.setImageResource(android.R.drawable.btn_star_big_on)
                Toast.makeText(this, "Article Bookmarked", Toast.LENGTH_SHORT).show()
            } else {
                bookmarkButton.setImageResource(android.R.drawable.btn_star)
                Toast.makeText(this, "Bookmark Removed", Toast.LENGTH_SHORT).show()
            }
        }

        // Share Button
        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check this article: How AI Will Change Our Daily Life in 2026")
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }
}