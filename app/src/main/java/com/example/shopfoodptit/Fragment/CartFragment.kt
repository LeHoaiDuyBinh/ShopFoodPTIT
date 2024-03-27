package com.example.shopfoodptit.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopfoodptit.PaymentActivity
import com.example.shopfoodptit.R
import com.example.shopfoodptit.adapter.CartAdapter
import com.example.shopfoodptit.databinding.FragmentCartBinding
import com.example.shopfoodptit.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import java.security.MessageDigest


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodName:MutableList<String>
    private lateinit var foodPrice:MutableList<String>
    private lateinit var foodImage:MutableList<String>
    private lateinit var foodTopping:MutableList<String>
    private lateinit var foodDescrip:MutableList<String>
    private lateinit var foodQuantity:MutableList<Int>
    private lateinit var foodNote:MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        retrieveCartItems()

        binding.btnCartConfirm.setOnClickListener {
            //get order item detail before proceeding for checkout
            getOrderItemsDetail()
//            val intent = Intent(requireContext(), PaymentActivity::class.java)
//            startActivity(intent)
        }

        return binding.root
    }

    private fun getOrderItemsDetail() {
        database = FirebaseDatabase.getInstance()
        val userID = sha1(auth.currentUser?.email?:"")
        val  orderIdRef:DatabaseReference = database.reference.child("User").child(userID).child("Cart")
        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodTopping = mutableListOf<String>()
        val foodDescrip = mutableListOf<String>()
        val foodNote = mutableListOf<String>()
        //get items quantity
        val foodQuantities = CartAdapter.getUpdatedItemsQuantities()

        orderIdRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    //get the cart items to respective list
                    val orderItems = foodSnapshot.getValue(CartItem::class.java)
                    //add items detail in to list
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodTopping?.let { foodTopping.add(it) }
                    orderItems?.foodDescription?.let { foodDescrip.add(it) }
                    orderItems?.foodNote?.let { foodNote.add(it) }
                }
                orderNow(foodName, foodPrice, foodDescrip, foodImage, foodTopping, foodQuantities, foodNote)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Order making FAILED. Please Try Again", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun orderNow(foodName: MutableList<String>, foodPrice: MutableList<String>, foodDescrip: MutableList<String>, foodImage: MutableList<String>, foodTopping: MutableList<String>, foodQuantities: MutableList<Int>, foodNote: MutableList<String>) {
        if(isAdded && context != null) {
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            intent.putExtra("foodItemName", foodName as ArrayList<String>)
            intent.putExtra("foodItemPrice", foodPrice as ArrayList<String>)
            intent.putExtra("foodItemDescription", foodDescrip as ArrayList<String>)
            intent.putExtra("foodItemImage", foodImage as ArrayList<String>)
            intent.putExtra("foodItemTopping", foodTopping as ArrayList<String>)
            intent.putExtra("foodItemQuantity", foodQuantities as ArrayList<Int>)
            intent.putExtra("foodItemNote", foodNote as ArrayList<String>)
            startActivity(intent)
        }
    }

    private fun retrieveCartItems() {
        //database reference to the firebase
        database = FirebaseDatabase.getInstance()
        val userID = sha1(auth.currentUser?.email?:"")
        val foodRef: DatabaseReference = database.reference.child("User").child(userID).child("Cart")

        //list to store cart items
        foodName = mutableListOf()
        foodPrice = mutableListOf()
        foodDescrip = mutableListOf()
        foodImage = mutableListOf()
        foodTopping = mutableListOf()
        foodQuantity = mutableListOf()
        foodNote = mutableListOf()

        //fetch data form the database
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    //get the cart items object to the children node
                    val cartItem = foodSnapshot.getValue(CartItem::class.java)
                    cartItem?.foodName?.let { foodName.add(it) }
                    cartItem?.foodPrice?.let { foodPrice.add(it) }
                    cartItem?.foodDescription?.let { foodDescrip.add(it) }
                    cartItem?.foodTopping?.let { foodTopping.add(it) }
                    cartItem?.foodImage?.let { foodImage.add(it) }
                    cartItem?.foodQuantity?.let { foodQuantity.add(it) }
                    cartItem?.foodNote?.let { foodNote.add(it) }
                }
                setAdapter()
            }

            private fun setAdapter() {
                val adapter = CartAdapter(requireContext(), foodName, foodPrice, foodDescrip, foodTopping, foodImage, foodQuantity)
                binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.cartRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not fetch!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun sha1(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-1").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val cartFoodName = listOf("Hamburger", "Pizza", "Gà KFC", "Hamburger", "Pizza", "Gà KFC")
//        val cartFoodAdd = listOf("Cà chua", "Phô mai", "Sốt", "Không có", "Không có", "Không có")
//        val cartFoodPrice = listOf("20.000 đ", "70.000 đ", "65.000 đ", "20.000 đ", "70.000 đ", "65.000 đ")
//        val cartFoodImages = listOf(R.drawable.pic_food_hamburger, R.drawable.pic_food_pizza, R.drawable.pic_food_kfc, R.drawable.pic_food_hamburger, R.drawable.pic_food_pizza, R.drawable.pic_food_kfc)
//        val adapter = CartAdapter(ArrayList(cartFoodName), ArrayList(cartFoodPrice), ArrayList(cartFoodImages), ArrayList(cartFoodAdd))
//        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.cartRecyclerView.adapter = adapter
//
//        binding.btnCartConfirm.setOnClickListener {
//            val intent = Intent(requireContext(), PaymentActivity::class.java)
//            startActivity(intent)
//        }
//    }

}
