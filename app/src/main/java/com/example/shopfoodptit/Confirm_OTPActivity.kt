package com.example.shopfoodptit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Confirm_OTPActivity : AppCompatActivity() {

    companion object {
        const val RANDOM_NUMBER_KEY = "random_number"
        const val EMAIL_KEY = "email"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_otpactivity)
        val edtOTP = findViewById<EditText>(R.id.edt_confirm_otp)

        val btnConfirm = findViewById<Button>(R.id.btn_confirm)
        btnConfirm.setOnClickListener {
            val randomNumber = intent.getStringExtra(RANDOM_NUMBER_KEY)
            val email = intent.getStringExtra(EMAIL_KEY)
            if (randomNumber.equals(edtOTP.text.toString())){
                val intent = Intent(this, NewPasswordActivity::class.java)
                intent.putExtra(EMAIL_KEY, email)
                startActivity(intent)
            }
        }
    }
}