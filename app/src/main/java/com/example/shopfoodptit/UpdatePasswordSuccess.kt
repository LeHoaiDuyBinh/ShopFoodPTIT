package com.example.shopfoodptit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class UpdatePasswordSuccess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password_success)

        val btnResetPasswordSuccess = findViewById<Button>(R.id.btn_compeleted_reset_password)
        btnResetPasswordSuccess.setOnClickListener {
            val intent = Intent(this, LoginUserActivity::class.java)
            startActivity(intent)
        }
    }
}