package com.example.themeal.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.themeal.constant.Constant

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@LaunchActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, Constant.ONE_SECOND)
    }
}
