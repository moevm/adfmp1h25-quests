package com.example.questcityproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
//import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.example.questcityproject.ui.AuthorsActivity
import com.example.questcityproject.ui.EnterActivity
import com.example.questcityproject.ui.RegistrationActivity

//import androidx.navigation.findNavController
//import androidx.navigation.ui.AppBarConfiguration
//import androidx.navigation.ui.setupActionBarWithNavController
//import androidx.navigation.ui.setupWithNavController
//import com.example.questcityproject.databinding.ActivityMainBinding

class StartActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.start_screen)

        val enterButton: Button = findViewById(R.id.loginButton)
        val registrationButton: Button = findViewById(R.id.registerButton)
        val aboutAuthorsButton: Button = findViewById(R.id.aboutAuthorsButton)

        enterButton.setOnClickListener {
            goToEnterPage()
        }

        registrationButton.setOnClickListener {
            goToRegistrationPage()
        }

        aboutAuthorsButton.setOnClickListener {
            goToAuthorsPage()
        }
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    private fun goToEnterPage() {
        val enter = Intent(this, EnterActivity::class.java)
        startActivity(enter)
    }

    private fun goToRegistrationPage() {
        val registration = Intent(this, RegistrationActivity::class.java)
        startActivity(registration)
    }

    private fun goToAuthorsPage() {
        val authors = Intent(this, AuthorsActivity::class.java)
        startActivity(authors)
    }
}