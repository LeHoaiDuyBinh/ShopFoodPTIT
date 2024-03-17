package com.example.shopfoodptit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopfoodptit.databinding.ToppingItemBinding

class ToppingAdapter(private val toppingItems:MutableList<String>, private val toppingPrices:MutableList<String>): RecyclerView.Adapter<ToppingAdapter.ToppingViewHolder>() {

    private val toppingQuestities = IntArray(toppingItems.size){0}
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ToppingViewHolder {
        val binding = ToppingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToppingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToppingAdapter.ToppingViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = toppingItems.size

    inner class ToppingViewHolder(private val binding: ToppingItemBinding):RecyclerView.ViewHolder(binding.root) {
        private fun deceaseQuantity (position: Int) {
            if(toppingQuestities[position] > 0) {
                toppingQuestities[position]--
                binding.toppingQuantity.text = toppingQuestities[position].toString()
            }
        }
        private fun increaseQuantity (position: Int) {
            toppingQuestities[position]++
            binding.toppingQuantity.text = toppingQuestities[position].toString()
        }
        fun bind(position: Int) {
            binding.apply {
                val quantity = toppingQuestities[position]
                toppingName.text = toppingItems[position]
                toppingPrice.text = toppingPrices[position]
                toppingQuantity.text = quantity.toString()
                btnToppingDec.setOnClickListener{
                    deceaseQuantity(position)
                }
                btnToppingInc.setOnClickListener {
                    increaseQuantity(position)
                }
            }
        }

    }

}