package com.example.questcityproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.questcityproject.R

class EnterActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var errorMessage: TextView

//    private val validUsername = "demo"
//    private val validPassword = "password"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        supportActionBar?.hide()

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {

//            resetErrorStates()
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString()
            goToMainPage()

//            if (validateCredentials(username, password)) {
//                // Successful login
//                goToMainPage()
//            } else {
//                // Show error state
//                showLoginError()
//            }
        }
//            if (validateInputs(login, email, password, passwordConfirm)) {
//                // Proceed with registration
//                proceedWithRegistration(login, email, password)
//            }
    }



//private fun resetErrorStates() {
//    usernameInput.isActivated = false
//    passwordInput.isActivated = false
//    errorMessage.visibility = View.GONE
//}
//
//private fun validateCredentials(username: String, password: String): Boolean {
//    // In a real app, you would check against a database or API
//    return username == validUsername && password == validPassword
//}
//
//private fun showLoginError() {
//    // Set red borders
//    usernameInput.isActivated = true
//    passwordInput.isActivated = true
//
//    // Show error message
//    errorMessage.text = "Неправильный логин или пароль"
//    errorMessage.visibility = View.VISIBLE
//}

private fun goToMainPage(){
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