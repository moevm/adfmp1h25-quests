package com.example.questcityproject.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.questcityproject.StartActivity
import com.example.questcityproject.R
import com.example.questcityproject.data.DatabaseHelper

class EnterActivity : AppCompatActivity() {

    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var errorTextView: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.login_screen)

        // Initialize database helper
        dbHelper = DatabaseHelper(this)

        // Initialize views
        loginEditText = findViewById(R.id.usernameInput)
        passwordEditText = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        errorTextView = findViewById(R.id.errorMessage)

        // Hide error message initially
        errorTextView.visibility = View.GONE

        // Set up login button click listener
        loginButton.setOnClickListener {
            validateLogin()
        }
    }

    private fun validateLogin() {
        val login = loginEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // Validate input fields
        if (login.isEmpty() || password.isEmpty()) {
            showError("Пожалуйста, заполните все поля")
            return
        }

        // Check credentials against database
        try {
            val isValidUser = dbHelper.checkUser(login, password)

            if (isValidUser) {
                // Login successful
                errorTextView.visibility = View.GONE

                // Navigate to main activity
                val intent = Intent(this, MainScreenActivity::class.java)
                startActivity(intent)
                finish() // Close login activity
            } else {
                // Login failed
                showError("Неправильный логин и/или пароль")
            }
        } catch (e: Exception) {
            Log.e("EnterActivity", "Database error: ${e.message}")
            showError("Ошибка при входе. Попробуйте позже.")
        }
    }

    private fun showError(message: String) {
        errorTextView.text = message
        errorTextView.visibility = View.VISIBLE
        loginEditText.setBackgroundResource(R.drawable.edit_text_background_error)
        passwordEditText.setBackgroundResource(R.drawable.edit_text_background_error)
    }
}