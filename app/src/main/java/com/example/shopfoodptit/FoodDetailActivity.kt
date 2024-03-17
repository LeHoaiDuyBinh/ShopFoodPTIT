package com.example.shopfoodptit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopfoodptit.adapter.ToppingAdapter
import com.example.shopfoodptit.databinding.ActivityFoodDetailBinding

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailBinding
    private val toppngingName = listOf(
        "Chả lụa", "Thịt bò", "Xúc xích", "Chả lụa", "Thịt bò", "Xúc xích")
    private val toppingPrice = listOf(
        "10.000 đ", "20.000 đ", "15.000 đ", "10.000 đ", "20.000 đ", "15.000 đ")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodName = intent.getStringExtra("menuItem")
        val foodImage = intent.getIntExtra("menuImage", 0)
        binding.foodNameDetail.text = foodName
        binding.foodImageDetail.setImageResource(foodImage)

        val adapter = ToppingAdapter(toppngingName as MutableList<String>, toppingPrice as MutableList<String>)
        binding.detailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.detailRecyclerView.adapter = adapter

        binding.btnFoodDetailOut.setOnClickListener{
            finish()
        }
    }
}