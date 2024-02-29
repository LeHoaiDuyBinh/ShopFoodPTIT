package com.example.shopfoodptit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopfoodptit.databinding.PopularItemBinding

class PopularAdapter (private val items:List<String>, private val prices:List<String>, private val images:List<Int>) : RecyclerView.Adapter<PopularAdapter.PouplerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PouplerViewHolder {
        return PouplerViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PouplerViewHolder, position: Int) {
        val item = items[position]
        val image = images[position]
        val price = prices[position]
        holder.bind(item, price, image)
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