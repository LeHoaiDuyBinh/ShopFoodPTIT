package com.example.shopfoodptit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class introduce_screen_2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduce_screen2)

        val btnStart = findViewById<Button>(R.id.btn_start)

        btnStart.setOnClickListener{
            val intent = Intent(this, LoginUserActivity::class.java)
            startActivity(intent)
        }
    }
}