package com.example.shopfoodptit.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopfoodptit.FoodDetailActivity
import com.example.shopfoodptit.databinding.PopularItemBinding
import android.content.Context
import android.view.View.OnClickListener

class PopularAdapter (private val items:List<String>, private val prices:List<String>, private val images:List<Int>, private val requireContext: Context) : RecyclerView.Adapter<PopularAdapter.PouplerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PouplerViewHolder {
        return PouplerViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PouplerViewHolder, position: Int) {
        val item = items[position]
        val image = images[position]
        val price = prices[position]
        holder.bind(item, price, image)

        holder.itemView.setOnClickListener{
            val intent = Intent(requireContext, FoodDetailActivity::class.java)
            intent.putExtra("menuItem", item)
            intent.putExtra("menuImage", image)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PouplerViewHolder(private val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView = binding.foodImagePopular
        fun bind(item: String, price: String, image: Int) {
            binding.foodNamePopular.text = item
            binding.foodPricePopular.text = price
            imageView.setImageResource(image)
        }

    }

}