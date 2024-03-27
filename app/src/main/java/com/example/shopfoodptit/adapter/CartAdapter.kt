package com.example.shopfoodptit.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopfoodptit.databinding.CartItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.content.Context
import android.graphics.drawable.Drawable
import android.media.MediaPlayer.OnCompletionListener
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.security.MessageDigest

class CartAdapter(
        private val context: Context,
        private val cartItems:MutableList<String>,
        private val cartPrice:MutableList<String>,
        private val cartDescriptions:MutableList<String>,
        private val cartToppingFoods:MutableList<String>,
        private val cartImages:MutableList<String>,
        private val cartQuantities:MutableList<Int>
    ) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    //instance firebase
    private val auth = FirebaseAuth.getInstance()
    init {
        val database = FirebaseDatabase.getInstance()
        val userID = sha1(auth.currentUser?.email?:"")
        val cartItemNumber = cartItems.size
        itemQuantities = IntArray(cartItemNumber){1}
        cartItemsReference = database.reference.child("User").child(userID).child("Cart")
    }

    fun sha1(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-1").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    companion object {
        fun getUpdatedItemsQuantities(): MutableList<Int> {
            val itemQuantities = mutableListOf<Int>()
            itemQuantities.addAll(cartQuantities)
            return itemQuantities
        }
        private var cartQuantities:MutableList<Int> = mutableListOf()
        private var itemQuantities:IntArray = intArrayOf()
        private lateinit var cartItemsReference: DatabaseReference
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size

    //get updated quantities
//    fun getUpdatedItemsQuantities(): MutableList<Int> {
//        val itemQuantities = mutableListOf<Int>()
//        itemQuantities.addAll(cartQuantities)
//        return itemQuantities
//    }

    inner class CartViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private fun deceaseQuantity (position: Int) {
            if(itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.cartQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun increaseQuantity (position: Int) {
            itemQuantities[position]++
            binding.cartQuantity.text = itemQuantities[position].toString()
        }
        private fun deleteItem (position: Int) {
            val positionRetrieve = position
            getUniqueKeyAtPosition(positionRetrieve) {uniqueKey ->
                if (uniqueKey != null) {
                    removeItem(position, uniqueKey)
                }

            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if(uniqueKey != null) {
                cartItemsReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    cartItems.removeAt(position)
                    cartPrice.removeAt(position)
                    cartDescriptions.removeAt(position)
                    cartImages.removeAt(position)
                    cartToppingFoods.removeAt(position)
                    cartQuantities.removeAt(position)

                    Toast.makeText(context, "Item deleted!", Toast.LENGTH_SHORT).show()

                    //update itemQuantities
                    itemQuantities = itemQuantities.filterIndexed{index, i -> index != position }.toIntArray()

                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, cartItems.size)
                }.addOnFailureListener{
                    Toast.makeText(context, "Failed to delete!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun getUniqueKeyAtPosition(positionRetrieve: Int, onComplete : (String?) -> Unit) {
            cartItemsReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey:String?=null
                    //loop for snapshot children
                    snapshot.children.forEachIndexed{index, dataSnapshot ->
                        if(index == positionRetrieve) {
                            uniqueKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                foodNameCart.text = cartItems[position]
                foodToppingCart.text = cartToppingFoods[position]
                foodPriceCart.text = cartPrice[position]
                //load image using glide
                val uriString = cartImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(foodImageCart)
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