package com.example.themeal.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.themeal.constant.Constant
import com.example.themeal.ui.home.HomeViewModel
import org.koin.android.ext.android.inject

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject<HomeViewModel>().value
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@LaunchActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, Constant.ONE_SECOND)
    }
}
