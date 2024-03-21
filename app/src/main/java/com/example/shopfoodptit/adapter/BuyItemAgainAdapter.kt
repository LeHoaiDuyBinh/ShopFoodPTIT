package com.example.shopfoodptit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopfoodptit.databinding.BuyItemAgainBinding
import java.util.ArrayList

class BuyItemAgainAdapter(private val buyAgainFoodName : ArrayList<String>, private val buyAgainFoodPrice : ArrayList<String>,
    private val buyAgainFoodImg : ArrayList<Int>) : RecyclerView.Adapter<BuyItemAgainAdapter.BuyAgainViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = BuyItemAgainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyAgainViewHolder(binding)
    }

    override fun getItemCount(): Int = buyAgainFoodName.size

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(buyAgainFoodName[position], buyAgainFoodPrice[position], buyAgainFoodImg[position])
    }

    class BuyAgainViewHolder(private val binding: BuyItemAgainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, foodPrice: String, foodImg : Int){
            binding.foodNameHistory.text = foodName
            binding.foodPriceHistory.text = foodPrice
            binding.foodImageHistory.setImageResource(foodImg)
        }

    }

}