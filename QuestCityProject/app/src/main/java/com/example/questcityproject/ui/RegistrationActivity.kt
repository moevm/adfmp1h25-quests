package com.example.questcityproject.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.questcityproject.R

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_screen)

        // Initialize views
        var loginInput: EditText = findViewById(R.id.loginInput)
        var emailInput: EditText = findViewById(R.id.emailInput)
        var passwordInput: EditText = findViewById(R.id.passwordInput)
        var passwordConfirmInput: EditText = findViewById(R.id.passwordConfirmInput)
        var nextButton: Button = findViewById(R.id.nextButton)

        nextButton.setOnClickListener {
            val login = loginInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val passwordConfirm = passwordConfirmInput.text.toString()


//            if (validateInputs(login, email, password, passwordConfirm)) {
//                // Proceed with registration
//                proceedWithRegistration(login, email, password)
//            }
        }
    }

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
}