package com.example.questcityproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.questcityproject.R
import android.graphics.drawable.AnimatedVectorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationActivity : AppCompatActivity() {

    private lateinit var loginInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var passwordConfirmInput: EditText
    private lateinit var nextButton: Button
    private lateinit var errorMessage: TextView

    // Declare these variables for the loading dialog
    private lateinit var loadingDialog: AlertDialog
    private lateinit var loadingAnimation: AnimatedVectorDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_screen)
        supportActionBar?.hide()

        // Initialize views
        loginInput = findViewById(R.id.loginInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        passwordConfirmInput = findViewById(R.id.passwordConfirmInput)
        nextButton = findViewById(R.id.nextButton)
        errorMessage = findViewById(R.id.errorMessage)

        nextButton.setOnClickListener {
            try {
                resetErrorStates()
                if(validateInputs()) {
                    showLoadingDialog()
                    performRegistration()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun goToMainPage() {
        val enter = Intent(this, EnterActivity::class.java)
        startActivity(enter)
    }

    private fun resetErrorStates() {
        loginInput.isActivated = false
        emailInput.isActivated = false
        passwordInput.isActivated = false
        passwordConfirmInput.isActivated = false
        errorMessage.visibility = View.GONE
    }

    private fun validateInputs(): Boolean {
        val login = loginInput.text.toString()
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val passwordConfirm = passwordConfirmInput.text.toString()

        // Check if fields are empty
        if (login.isEmpty()) {
            showError("Введите логин", loginInput)
            return false
        }

        if (email.isEmpty()) {
            showError("Введите email", emailInput)
            return false
        }

        if (password.isEmpty()) {
            showError("Введите пароль", passwordInput)
            return false
        }

        if (passwordConfirm.isEmpty()) {
            showError("Подтвердите пароль", passwordConfirmInput)
            return false
        }

        // Check if passwords match
        if (password != passwordConfirm) {
            showPasswordMismatchError()
            return false
        }

        // Check password length
        if (password.length < 6) {
            showError("Пароль должен содержать минимум 6 символов", passwordInput, passwordConfirmInput)
            return false
        }

        return true
    }

    private fun showError(message: String, vararg fields: EditText) {
        // Set red borders for specified fields
        fields.forEach { it.isActivated = true }
        // Show error message
        errorMessage.text = message
        errorMessage.visibility = View.VISIBLE
    }

    private fun showPasswordMismatchError() {
        // Set red borders for password fields
        passwordInput.isActivated = true
        passwordConfirmInput.isActivated = true

        // Show error message
        errorMessage.text = "Пароли не совпадают"
        errorMessage.visibility = View.VISIBLE
    }

    private fun showLoadingDialog() {
        try {
            // Create a custom view for the loading dialog
            val dialogView = layoutInflater.inflate(R.layout.dialog_loading, null) // Use R.layout, not R.drawable
            val loadingImageView = dialogView.findViewById<ImageView>(R.id.loadingImageView)

            // Set up and start the loading animation
            loadingAnimation = ContextCompat.getDrawable(this, R.drawable.loading_animation) as AnimatedVectorDrawable
            loadingImageView.setImageDrawable(loadingAnimation)
            loadingAnimation.start()

            // Create and show the dialog
            loadingDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create()

            loadingDialog.show()
        } catch (e: Exception) {
            // If there's an error with the loading dialog, just log it and continue
            // This prevents the app from crashing if there's an issue with the dialog
            Toast.makeText(this, "Регистрация...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun performRegistration() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Simulate network delay
                delay(2000)

                withContext(Dispatchers.Main) {
                    try {
                        // Only dismiss if the dialog was successfully shown
                        if (::loadingDialog.isInitialized && loadingDialog.isShowing) {
                            loadingDialog.dismiss()
                        }
                    } catch (e: Exception) {
                        // Ignore any errors when dismissing
                    }

                    showSuccessDialog()
                }
            }
            catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    try {
                        // Only dismiss if the dialog was successfully shown
                        if (::loadingDialog.isInitialized && loadingDialog.isShowing) {
                            loadingDialog.dismiss()
                        }
                    } catch (e: Exception) {
                        // Ignore any errors when dismissing
                    }

                    showErrorDialog("Произошла ошибка: ${e.message}")
                }
            }
        }
    }

    private fun showSuccessDialog() {
        val dialog = AlertDialog.Builder(this)
            .setMessage("Регистрация прошла успешно!")
            .setPositiveButton("OK") { _, _ ->
                navigateToNextScreen()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    private fun showErrorDialog(errorMessage: String) {
        val dialog = AlertDialog.Builder(this)
            .setMessage(errorMessage)
            .setPositiveButton("OK", null)
            .create()

        dialog.show()
    }

    private fun navigateToNextScreen() {
        goToMainPage()
    }
}

// Extension function for dialog text color
fun AlertDialog.Builder.setTextColor(color: Int): AlertDialog.Builder {
    val dialog = this.create()
    dialog.setOnShowListener {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(color)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(color)
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL)?.setTextColor(color)
    }
    return this
}