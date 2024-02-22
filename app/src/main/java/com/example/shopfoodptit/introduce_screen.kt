package com.example.shopfoodptit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

@Suppress("DEPRECATION")
class introduce_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduce_screen)

        Handler().postDelayed({
            val intent = Intent(this, introduce_screen_2::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}