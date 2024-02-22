package com.example.shopfoodptit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnSendCode = findViewById<Button>(R.id.btn_send_code)
        btnSendCode.setOnClickListener {
            val intent = Intent(this, Confirm_OTPActivity::class.java)
            startActivity(intent)
        }
    }
}