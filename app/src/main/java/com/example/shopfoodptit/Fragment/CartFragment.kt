package com.example.shopfoodptit.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopfoodptit.PaymentActivity
import com.example.shopfoodptit.R
import com.example.shopfoodptit.adapter.CartAdapter
import com.example.shopfoodptit.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartFoodName = listOf("Hamburger", "Pizza", "Gà KFC", "Hamburger", "Pizza", "Gà KFC")
        val cartFoodAdd = listOf("Cà chua", "Phô mai", "Sốt", "Không có", "Không có", "Không có")
        val cartFoodPrice = listOf("20.000 đ", "70.000 đ", "65.000 đ", "20.000 đ", "70.000 đ", "65.000 đ")
        val cartFoodImages = listOf(R.drawable.pic_food_hamburger, R.drawable.pic_food_pizza, R.drawable.pic_food_kfc, R.drawable.pic_food_hamburger, R.drawable.pic_food_pizza, R.drawable.pic_food_kfc)
        val adapter = CartAdapter(ArrayList(cartFoodName), ArrayList(cartFoodPrice), ArrayList(cartFoodImages), ArrayList(cartFoodAdd))
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter

        binding.btnCartConfirm.setOnClickListener {
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            startActivity(intent)
        }
    }

}