package com.example.shopfoodptit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shopfoodptit.Fragment.CartFragment
import com.example.shopfoodptit.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnPaymentAccept.setOnClickListener {
            val intent = Intent(this, ConfirmOrder::class.java)
            startActivity(intent)
        }
        binding.btnPaymentOut.setOnClickListener {
            val intent = Intent(this, CartFragment::class.java)
            startActivity(intent)
        }
    }
}