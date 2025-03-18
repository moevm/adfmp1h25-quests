package com.example.questcityproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.questcityproject.ui.AuthorsActivity
import com.example.questcityproject.ui.EnterActivity
import com.example.questcityproject.ui.RegistrationActivity

class StartActivity : AppCompatActivity() {

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

        // Check if it's the first time launch
        if (isFirstTimeLaunch()) {
            showFirstTimeDialog()
        }
    }

    private fun isFirstTimeLaunch(): Boolean {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("is_first_time_launch", true)
        return isFirstTime
    }

    private fun setFirstTimeLaunchComplete() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_first_time_launch", false)
        editor.apply()
    }

    private fun showFirstTimeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_first_time, null)
        val safetyCheckbox = dialogView.findViewById<CheckBox>(R.id.safetyCheckbox)
        val continueButton = dialogView.findViewById<Button>(R.id.continueButton)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        // Enable/disable continue button based on checkbox state
        safetyCheckbox.setOnCheckedChangeListener { _, isChecked ->
            continueButton.isEnabled = isChecked
            continueButton.alpha = if (isChecked) 1.0f else 0.5f
        }

        // Set continue button click listener
        continueButton.setOnClickListener {
            // Mark first time launch as complete
            setFirstTimeLaunchComplete()
            dialog.dismiss()
        }

        dialog.show()
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