package com.example.questcityproject

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class StartAppActivity : AppCompatActivity() {
    private var to_login_button: Button? = null
    private var to_registration_button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_start_screen)

        to_login_button = findViewById(R.id.loginButton)
        to_registration_button = findViewById(R.id.registerButton)

        to_login_button?.setOnClickListener {
            view -> view.findNavController().navigate(R.id.action_navigation_start_to_navigation_login)
        }
//        to_login_button?.setOnClickListener(
//            Navigation.createNavigateOnClickListener(R.id.action_navigation_start_to_navigation_login, null)
//        )
        to_registration_button?.setOnClickListener {
            view -> view.findNavController().navigate(R.id.action_navigation_start_to_navigation_registration)
        }
    }
}