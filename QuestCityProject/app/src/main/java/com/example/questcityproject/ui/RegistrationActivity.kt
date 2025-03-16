package com.example.questcityproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.questcityproject.R
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Handler
import android.os.Looper
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_screen)
        supportActionBar?.hide()
        // Initialize views

        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        passwordConfirmInput = findViewById(R.id.passwordConfirmInput)
        nextButton = findViewById(R.id.nextButton)
        errorMessage = findViewById(R.id.errorMessage)

        nextButton.setOnClickListener {
            resetErrorStates()
            if(validateInputs()) {
                showLoadingDialog()
                performRegistration()
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

    private lateinit var loadingDialog: AlertDialog
    private lateinit var loadingAnimation: AnimatedVectorDrawable

    @SuppressLint("ResourceType")
    private fun showLoadingDialog() {
                    // Create a custom view for the loading dialog
        val dialogView = layoutInflater.inflate(R.drawable.dialog_loading, null)
        val loadingImageView = dialogView.findViewById<ImageView>(R.id.loadingImageView)

                    // Set up and start the loading animation
        loadingAnimation = ContextCompat.getDrawable(this, R.drawable.loading_animation) as AnimatedVectorDrawable
        loadingImageView.setImageDrawable(loadingAnimation)
        loadingAnimation.start()

                    // Create and show the dialog
        loadingDialog = AlertDialog.Builder(this).setView(dialogView).setCancelable(false).create()

        loadingDialog.show()
    }
    private fun performRegistration() {
                    // Use coroutines to simulate network call
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Simulate network delay
                delay(2000)

                // Simulate success (90% chance) or failure (10% chance)
//                val isSuccess = (0..9).random() < 9
                withContext(Dispatchers.Main) {
                    loadingDialog.dismiss()
                    showSuccessDialog()
////                    if (isSuccess) {
//                        showSuccessDialog()
//                    } else {
//                        showErrorDialog("Ошибка! Попробуйте позже!")
                }
            }
            catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    loadingDialog.dismiss()
                    showErrorDialog("Произошла ошибка: ${e.message}")
                }
            }
        }
    }

    private fun showSuccessDialog() {
        val dialog = AlertDialog.Builder(this)
            .setMessage("Регистрация прошла успешно!")
            .setPositiveButton("OK") { _, _ ->
                            // Navigate to the next screen
                navigateToNextScreen()
            }
                .setCancelable(false)
                .create()

        dialog.show()
    }

    private fun showErrorDialog(errorMessage: String) {
        val dialog = AlertDialog.Builder(this)
            .setMessage(errorMessage)
            .setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
            .setPositiveButton("OK", null)
            .create()

        dialog.show()
    }

    private fun navigateToNextScreen() {
        goToMainPage()
    }
}

fun AlertDialog.Builder.setTextColor(color: Int): AlertDialog.Builder {
    val dialog = this.create()
    dialog.setOnShowListener {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(color)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(color)
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL)?.setTextColor(color)
    }
    return this
}
// Extension function to set text color for AlertDialog

//            if (validateInputs(login, email, password, passwordConfirm)) {
//                // Proceed with registration
//                proceedWithRegistration(login, email, password)
//            }
//        }
//    }

//    private fun validateInputs(
//        login: String,
//        email: String,
//        password: String,
//        passwordConfirm: String
//    ): Boolean {
//        // Add your validation logic here
//        return true
//    }
//
//    private fun proceedWithRegistration(login: String, email: String, password: String) {
//        // Implement your registration logic here
//        // Then navigate to the next screen
//    }