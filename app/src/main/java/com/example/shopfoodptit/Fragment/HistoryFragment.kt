package com.example.shopfoodptit.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopfoodptit.R
import com.example.shopfoodptit.adapter.BuyItemAgainAdapter
import com.example.shopfoodptit.databinding.BuyItemAgainBinding
import com.example.shopfoodptit.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyItemAgainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        setUpRecycleView()
        return binding.root
    }

    private fun setUpRecycleView() {
        val buyAgainFoodName = arrayListOf("Hambuger", "Pizza", "Gà KFC")
        val buyAgainFoodPrice = arrayListOf("10.000đ", "10.000đ", "10.000đ")
        val buyAgainFoodImg = arrayListOf(R.drawable.pic_food_hamburger, R.drawable.pic_food_pizza, R.drawable.pic_food_kfc)
        buyAgainAdapter = BuyItemAgainAdapter(buyAgainFoodName, buyAgainFoodPrice, buyAgainFoodImg)
        binding.revItemHistory.adapter = buyAgainAdapter
        binding.revItemHistory.layoutManager = LinearLayoutManager(requireContext())
    }

}