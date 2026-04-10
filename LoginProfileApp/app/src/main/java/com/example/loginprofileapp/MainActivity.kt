package com.example.loginprofileapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var ivLogo: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvForgot: TextView
    private lateinit var btnLogin: Button
    private lateinit var profileCard: RelativeLayout
    private lateinit var btnLogout: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivLogo = findViewById(R.id.iv_logo)
        tvTitle = findViewById(R.id.tv_title)
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        tvForgot = findViewById(R.id.tv_forgot)
        btnLogin = findViewById(R.id.btn_login)
        profileCard = findViewById(R.id.profile_card)
        btnLogout = findViewById(R.id.btn_logout)
        progressBar = findViewById(R.id.progress_bar)


        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username == "admin" && password == "1234") {
                progressBar.visibility = View.VISIBLE
                btnLogin.isEnabled = false


                Handler(Looper.getMainLooper()).postDelayed({
                    progressBar.visibility = View.GONE

                    ivLogo.visibility = View.GONE
                    tvTitle.visibility = View.GONE
                    etUsername.visibility = View.GONE
                    etPassword.visibility = View.GONE
                    tvForgot.visibility = View.GONE
                    btnLogin.visibility = View.GONE

                    profileCard.visibility = View.VISIBLE
                }, 1800)
            } else {
                Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_SHORT).show()
            }
        }


        btnLogout.setOnClickListener {

            profileCard.visibility = View.GONE
            ivLogo.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            etUsername.visibility = View.VISIBLE
            etPassword.visibility = View.VISIBLE
            tvForgot.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE
            btnLogin.isEnabled = true

            etUsername.text.clear()
            etPassword.text.clear()
        }

        tvForgot.setOnClickListener {
            Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_LONG).show()
        }
    }
}