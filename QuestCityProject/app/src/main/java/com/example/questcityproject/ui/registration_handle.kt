package com.example.questcityproject.ui

class RegistrationActivity : AppCompatActivity() {
    private lateinit var loginInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var passwordConfirmInput: EditText
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Initialize views
        loginInput = findViewById(R.id.loginInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        passwordConfirmInput = findViewById(R.id.passwordConfirmInput)
        nextButton = findViewById(R.id.nextButton)

        nextButton.setOnClickListener {
            val login = loginInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val passwordConfirm = passwordConfirmInput.text.toString()

            if (validateInputs(login, email, password, passwordConfirm)) {
                // Proceed with registration
                proceedWithRegistration(login, email, password)
            }
        }
    }

    private fun validateInputs(
        login: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Boolean {
        // Add your validation logic here
        return true
    }

    private fun proceedWithRegistration(login: String, email: String, password: String) {
        // Implement your registration logic here
        // Then navigate to the next screen
    }
}