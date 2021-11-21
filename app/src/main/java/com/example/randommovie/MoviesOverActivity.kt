package com.example.randommovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MoviesOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_over)
    }

    fun backToMain(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}