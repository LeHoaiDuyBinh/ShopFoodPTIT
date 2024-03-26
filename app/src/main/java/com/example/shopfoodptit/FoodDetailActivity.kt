package com.example.shopfoodptit

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.shopfoodptit.adapter.ToppingAdapter
import com.example.shopfoodptit.databinding.ActivityFoodDetailBinding
import com.example.shopfoodptit.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailBinding
    private lateinit var auth: FirebaseAuth
    private var foodName:String ?= null
    private var foodDescript:String ?= null
    private var foodPrice:String ?= null
    private var foodIngred:String ?= null
    private var foodImage:String ?= null
    private val toppingName = listOf(
        "Chả lụa", "Thịt bò", "Xúc xích", "Chả lụa", "Thịt bò")
    private val toppingPrice = listOf(
        "10.000 đ", "20.000 đ", "15.000 đ", "10.000 đ", "20.000 đ")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize firebase
        auth = FirebaseAuth.getInstance()

        foodName = intent.getStringExtra("menuItemName")
        foodDescript = intent.getStringExtra("menuItemDescription")
        foodPrice = intent.getStringExtra("menuItemPrice")
        foodIngred = intent.getStringExtra("menuItemIngredients")
        foodImage = intent.getStringExtra("menuItemImage")

        val adapter = ToppingAdapter(toppingName as MutableList<String>, toppingPrice as MutableList<String>)
        binding.detailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.detailRecyclerView.adapter = adapter

        with(binding) {
            foodNameDetail.text = foodName
            foodDecription.text = foodDescript
            Glide.with(this@FoodDetailActivity).load(Uri.parse(foodImage)).into(foodImageDetail)
        }

        binding.btnFoodDetailOut.setOnClickListener{
            finish()
        }

        binding.btnAddToCart.setOnClickListener{
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userID = auth.currentUser?.uid?:""

        //create a cart items object
        val cartItem = CartItem(foodName.toString(), foodPrice.toString(), foodDescript.toString(), foodImage.toString(), "topping", 1 )

        //save data to cart to database
        database.child("User").child(userID).child("Cart").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this, "Items added into cart successfully!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Items no added!", Toast.LENGTH_SHORT).show()
        }
    }
}