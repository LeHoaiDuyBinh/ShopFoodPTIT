package com.example.shopfoodptit.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopfoodptit.FoodDetailActivity
import com.example.shopfoodptit.databinding.MenuItemBinding
import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide
import com.example.shopfoodptit.model.MenuItem

class MenuAdapter(private val menuItems:List<MenuItem>, private val requireContext: Context): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

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
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem:MenuItem = menuItems[position]
            // a intent to open details activity and pass data
            val intent:Intent = Intent(requireContext, FoodDetailActivity::class.java).apply {
                putExtra("menuItemName", menuItem.foodName)
                putExtra("menuItemDescription", menuItem.foodDescription)
                putExtra("menuItemPrice", menuItem.foodPrice)
                putExtra("menuItemIngredients", menuItem.foodIngredients)
                putExtra("menuItemImage", menuItem.foodImage)
            }

            //start the details activity
            requireContext.startActivity(intent)
        }

        //set data in to recyclerview items name, price, image
        fun bind(position: Int) {
            val menuItem:MenuItem = menuItems[position]
            binding.apply {
                foodNameMenu.text = menuItem.foodName
                foodPriceMenu.text = menuItem.foodPrice
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(foodImageMenu)
            }
        }
    }
}
