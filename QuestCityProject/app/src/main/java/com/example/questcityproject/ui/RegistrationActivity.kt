package com.example.questcityproject.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.questcityproject.StartActivity
import com.example.questcityproject.R
import com.example.questcityproject.data.DatabaseHelper

class RegistrationActivity : AppCompatActivity() {

    private lateinit var loginEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var errorTextView: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.registration_screen)

        // Initialize database helper
        dbHelper = DatabaseHelper(this)

        // Initialize views
        loginEditText = findViewById(R.id.loginInput)
        emailEditText = findViewById(R.id.emailInput)
        passwordEditText = findViewById(R.id.passwordInput)
        confirmPasswordEditText = findViewById(R.id.passwordConfirmInput)
        registerButton = findViewById(R.id.nextButton)
        errorTextView = findViewById(R.id.errorMessage)

        // Hide error message initially
        errorTextView.visibility = View.GONE

        // Set up register button click listener
        registerButton.setOnClickListener {
            validateAndRegister()
        }
    }

    private fun validateAndRegister() {
        // Reset error states
        resetErrorStates()

        val login = loginEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        // Validate input fields
        if (login.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Пожалуйста, заполните все поля")
            return
        }

        // Validate email format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Пожалуйста, введите корректный email")
            emailEditText.setBackgroundResource(R.drawable.edit_text_background_error)
            return
        }

        // Check if passwords match
        if (password != confirmPassword) {
            showError("Пароли не совпадают")
            passwordEditText.setBackgroundResource(R.drawable.edit_text_background_error)
            confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_background_error)
            return
        }

        // Check if login already exists
        if (dbHelper.checkLoginExists(login)) {
            showError("Пользователь с таким логином уже существует")
            loginEditText.setBackgroundResource(R.drawable.edit_text_background_error)
            return
        }

        // Check if email already exists
        if (dbHelper.checkEmailExists(email)) {
            showError("Пользователь с таким email уже существует")
            emailEditText.setBackgroundResource(R.drawable.edit_text_background_error)
            return
        }

        // All validations passed, register the user
        try {
            val userId = dbHelper.addUser(login, email, password)

            if (userId != -1L) {
                // Registration successful
                errorTextView.visibility = View.GONE

                // Navigate to main activity
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
                finish() // Close registration activity
            } else {
                // Registration failed
                showError("Ошибка при регистрации. Попробуйте позже.")
            }
        } catch (e: Exception) {
            Log.e("RegistrationActivity", "Database error: ${e.message}")
            showError("Ошибка при регистрации. Попробуйте позже.")
        }
    }

    private fun resetErrorStates() {
        errorTextView.visibility = View.GONE
        loginEditText.setBackgroundResource(R.drawable.edit_text_background)
        emailEditText.setBackgroundResource(R.drawable.edit_text_background)
        passwordEditText.setBackgroundResource(R.drawable.edit_text_background)
        confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_background)
    }

    private fun showError(message: String) {
        errorTextView.text = message
        errorTextView.visibility = View.VISIBLE
    }
}