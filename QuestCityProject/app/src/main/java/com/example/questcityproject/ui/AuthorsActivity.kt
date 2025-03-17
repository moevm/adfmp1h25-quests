package com.example.questcityproject.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.questcityproject.R

class AuthorsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_authors)

        // Get references to the ImageViews
        val authorImage1 = findViewById<ImageView>(R.id.authorImage1)
        val authorImage2 = findViewById<ImageView>(R.id.authorImage2)
        val authorImage3 = findViewById<ImageView>(R.id.authorImage3)


        authorImage1.setImageResource(R.drawable.artem)
        authorImage2.setImageResource(R.drawable.shadowthehedgehog)
        authorImage3.setImageResource(R.drawable.dima)
    }
}