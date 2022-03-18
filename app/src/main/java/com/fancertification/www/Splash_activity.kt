package com.fancertification.www

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class Splash_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}