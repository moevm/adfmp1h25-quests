package com.example.questcityproject.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.questcityproject.R
import com.example.questcityproject.ui.MainScreenActivity


class EnterActivity : AppCompatActivity() {
    private var usernameInput: EditText? = null
    private var passwordInput: EditText? = null
    private var loginButton: Button? = null
    private var errorMessage: TextView? = null

    // For demo purposes, hardcoded credentials
    private val validUsername = "demo"
    private val validPassword = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        try {
            setContentView(R.layout.login_screen)

            // Initialize views with null safety
            usernameInput = findViewById(R.id.usernameInput)
            passwordInput = findViewById(R.id.passwordInput)
            loginButton = findViewById(R.id.loginButton)

            // Try to find the error message TextView, but don't crash if it's not there
            try {
                errorMessage = findViewById(R.id.errorMessage)
            } catch (e: Exception) {
                Log.w("LoginActivity", "Error message TextView not found: ${e.message}")
                // Continue without the error message TextView
            }

            // Set up button click listener with null safety
            loginButton?.setOnClickListener {
                try {
                    // Reset error states
                    resetErrorStates()

                    val username = usernameInput?.text?.toString()?.trim() ?: ""
                    val password = passwordInput?.text?.toString() ?: ""

                    if (validateCredentials(username, password)) {
                        // Successful login
                        navigateToMainScreen()
                    } else {
                        // Show error state
                        showLoginError()
                    }
                } catch (e: Exception) {
                    Log.e("LoginActivity", "Error in login button click: ${e.message}", e)
                    Toast.makeText(this, "Произошла ошибка: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error in onCreate: ${e.message}", e)
            Toast.makeText(this, "Ошибка при инициализации: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun resetErrorStates() {
        try {
            usernameInput?.isActivated = false
            passwordInput?.isActivated = false

            // Hide error message if it exists
            errorMessage?.visibility = View.GONE
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error in resetErrorStates: ${e.message}", e)
        }
    }

    private fun validateCredentials(username: String, password: String): Boolean {
        // Check if fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            return false
        }

        // In a real app, you would check against a database or API
        return username == validUsername && password == validPassword
    }

    private fun showLoginError() {
        try {
            // Set red borders
            usernameInput?.isActivated = true
            passwordInput?.isActivated = true

            // Show error message if the TextView exists
            if (errorMessage != null) {
                errorMessage?.text = "Неправильный логин или пароль"
                errorMessage?.visibility = View.VISIBLE
            } else {
                // Fallback to Toast if TextView doesn't exist
                Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error in showLoginError: ${e.message}", e)
            // Fallback to simple Toast
            Toast.makeText(this, "Ошибка входа", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMainScreen() {
        try {
            // Make sure you have the correct activity class name here
            goToMainPage()
            finish() // Close the login activity
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error in navigation: ${e.message}", e)
            Toast.makeText(this, "Ошибка при переходе: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun goToMainPage(){
        val enter = Intent(this, MainScreenActivity::class.java)
        startActivity(enter)
    }
}