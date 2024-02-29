package com.example.shopfoodptit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopfoodptit.databinding.CartItemBinding

class CartAdapter(
        private val cartItems:MutableList<String>,
        private val cartPrice:MutableList<String>,
        private val cartImages:MutableList<Int>,
        private val cartAddFoods:MutableList<String>
    ) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private val itemQuestities = IntArray(cartItems.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    inner class CartViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private fun deceaseQuantity (position: Int) {
            if(itemQuestities[position] > 1) {
                itemQuestities[position]--
                binding.cartQuantity.text = itemQuestities[position].toString()
            }
        }
        private fun increaseQuantity (position: Int) {
            itemQuestities[position]++
            binding.cartQuantity.text = itemQuestities[position].toString()
        }
        private fun deleteItem (position: Int) {
            cartItems.removeAt(position)
            cartImages.removeAt(position)
            cartPrice.removeAt(position)
            cartAddFoods.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)
        }
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuestities[position]
                foodNameCart.text = cartItems[position]
                foodAddCart.text = cartAddFoods[position]
                foodPriceCart.text = cartPrice[position]
                foodImageCart.setImageResource(cartImages[position])
                cartQuantity.text = quantity.toString()
                btnCartDec.setOnClickListener {
                    deceaseQuantity(position)
                }
                btnCartInc.setOnClickListener {
                    increaseQuantity(position)
                }
                btnCartDel.setOnClickListener {
                    val itemPosition = adapterPosition
                    if(itemPosition != RecyclerView.NO_POSITION)
                        deleteItem(itemPosition)
                }
            }
        }

    }
}