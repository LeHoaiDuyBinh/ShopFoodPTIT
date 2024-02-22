package com.example.shopfoodptit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var navItem = findNavController(R.id.fcv_view)
        var bnvItem = findViewById<BottomNavigationView>(R.id.bnv_menu)
        bnvItem.setupWithNavController(navItem)
    }
}