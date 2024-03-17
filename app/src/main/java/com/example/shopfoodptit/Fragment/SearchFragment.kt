package com.example.shopfoodptit.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopfoodptit.R
import com.example.shopfoodptit.adapter.MenuAdapter
import com.example.shopfoodptit.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private val originalMenuFoodName = listOf(
        "Hamburger", "Pizza", "Gà KFC", "Hamburger", "Pizza", "Gà KFC",
        "Hamburger", "Pizza", "Gà KFC", "Hamburger", "Pizza", "Gà KFC"
    )
    private val originalMenuFoodPrice = listOf(
        "20.000 đ", "70.000 đ", "65.000 đ", "20.000 đ", "70.000 đ", "65.000 đ",
        "20.000 đ", "70.000 đ", "65.000 đ", "20.000 đ", "70.000 đ", "65.000 đ"
    )
    private val originalMenuFoodImage = listOf(
        R.drawable.pic_food_hamburger, R.drawable.pic_food_pizza, R.drawable.pic_food_kfc,
        R.drawable.pic_food_hamburger, R.drawable.pic_food_pizza, R.drawable.pic_food_kfc,
        R.drawable.pic_food_hamburger, R.drawable.pic_food_pizza, R.drawable.pic_food_kfc,
        R.drawable.pic_food_hamburger, R.drawable.pic_food_pizza, R.drawable.pic_food_kfc
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val filterMenuFoodName = mutableListOf<String>()
    private val filterMenuFoodPrice = mutableListOf<String>()
    private val filterMenuFoodImage = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = MenuAdapter(filterMenuFoodName, filterMenuFoodPrice, filterMenuFoodImage, requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter

        setupSearchView()

        showAllMenu()

        return binding.root
    }

    private fun showAllMenu() {
        filterMenuFoodName.clear()
        filterMenuFoodPrice.clear()
        filterMenuFoodImage.clear()

        filterMenuFoodName.addAll(originalMenuFoodName)
        filterMenuFoodPrice.addAll(originalMenuFoodPrice)
        filterMenuFoodImage.addAll(originalMenuFoodImage)

        adapter.notifyDataSetChanged()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItem(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItem(newText)
                return true
            }
        })
    }

    private fun filterMenuItem(query: String) {
        filterMenuFoodName.clear()
        filterMenuFoodPrice.clear()
        filterMenuFoodImage.clear()

        originalMenuFoodName.forEachIndexed{ index, foodName ->
            if(foodName.contains(query, ignoreCase = true)) {
                filterMenuFoodName.add(foodName)
                filterMenuFoodPrice.add(originalMenuFoodPrice[index])
                filterMenuFoodImage.add(originalMenuFoodImage[index])
            }
        }
        adapter.notifyDataSetChanged()
    }

}