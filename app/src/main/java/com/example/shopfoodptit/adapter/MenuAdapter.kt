package com.example.shopfoodptit.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopfoodptit.FoodDetailActivity
import com.example.shopfoodptit.databinding.MenuItemBinding
import android.content.Context
import android.view.View.OnClickListener

class MenuAdapter(private val menuItems: MutableList<String>, private val menuItemPrices:MutableList<String>, private val menuItemImages: MutableList<Int>, private val requireContext: Context): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private val itemClickListener:OnClickListener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(position)
                }
                //set onclick listener to open detail
                val intent = Intent(requireContext, FoodDetailActivity::class.java)
                intent.putExtra("menuItem", menuItems.get(position))
                intent.putExtra("menuImage", menuItemImages.get(position))
                requireContext.startActivity(intent)
            }
        }
        fun bind(position: Int) {
            binding.apply {
                foodNameMenu.text = menuItems[position]
                foodPriceMenu.text = menuItemPrices[position]
                foodImageMenu.setImageResource(menuItemImages[position])
            }
        }
    }
    interface OnClickListener {
        fun onItemClick(position: Int)
    }
}

private fun OnClickListener?.onItemClick(position: Int) {
    TODO("something")
}
