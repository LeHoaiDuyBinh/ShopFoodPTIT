package com.example.shopfoodptit.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopfoodptit.adapter.MenuAdapter
import com.example.shopfoodptit.databinding.FragmentSearchBinding
import com.example.shopfoodptit.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var database:FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val menuItems = mutableListOf<MenuItem>()
    private val filterMenuItems = mutableListOf<MenuItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        retrieveMenuItems()

        setupSearchView()

        showAllMenu()

        return binding.root
    }

    private fun retrieveMenuItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference = database.reference.child("menu")
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {menuItems.add(it)}
                }
                Log.d("ITEMS", "onDataChange: Data received")
                // once data receive, add to adapter
                setAdapter()
            }

            private fun setAdapter() {
                if(menuItems.isNotEmpty()) {
                    val adapter = MenuAdapter(menuItems, requireContext())
                    binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.menuRecyclerView.adapter = adapter
                    Log.d("ITEMS", "setAdapter: data set")
                } else {
                    Log.d("ITEMS", "setAdapter: data NO set")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun showAllMenu() {
        filterMenuItems.clear()

        val adapter = MenuAdapter(menuItems, requireContext())
        for (menuItem in menuItems) {
            menuItem?.let { filterMenuItems.add(it) }
        }

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
        filterMenuItems.clear()

//        val adapter = MenuAdapter(menuItems, requireContext())
        menuItems.forEach { menuItem ->
            menuItem.foodName?.let { foodName ->
                if (foodName.contains(query, ignoreCase = true)) {
                    menuItem?.let { filterMenuItems.add(it) }
                }
            }
        }
        val adapter = MenuAdapter(filterMenuItems, requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}