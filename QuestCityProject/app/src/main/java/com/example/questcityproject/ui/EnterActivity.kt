package com.example.questcityproject.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.questcityproject.R

class EnterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        // Initialize views
        val loginInput: EditText = findViewById(R.id.usernameInput)
        val passwordInput: EditText = findViewById(R.id.passwordInput)
        val nextButton: Button = findViewById(R.id.loginButton)

        nextButton.setOnClickListener {
            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()

            goToMainPage()
//            if (validateInputs(login, email, password, passwordConfirm)) {
//                // Proceed with registration
//                proceedWithRegistration(login, email, password)
//            }
        }
    }

    private fun goToMainPage() {
        val enter = Intent(this, MainScreenActivity::class.java)
        startActivity(enter)
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