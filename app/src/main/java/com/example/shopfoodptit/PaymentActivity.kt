package com.example.shopfoodptit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shopfoodptit.Fragment.CartFragment
import com.example.shopfoodptit.databinding.ActivityPaymentBinding
import com.example.shopfoodptit.model.OrderDetail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.security.MessageDigest

class PaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityPaymentBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var userID: String
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var totalAmount: String
    private lateinit var foodItemName: ArrayList<String>
    private lateinit var foodItemPrice: ArrayList<String>
    private lateinit var foodItemDescription: ArrayList<String>
    private lateinit var foodItemImage: ArrayList<String>
    private lateinit var foodItemTopping: ArrayList<String>
    private lateinit var foodItemQuantity: ArrayList<Int>
    private lateinit var foodItemNote: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize firebase and user detail
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference()

        //set user data
        setUserData()

        //get user details from firebase
//        val intent = intent
        foodItemName = intent.getStringArrayListExtra("foodItemName") as ArrayList<String>
        foodItemPrice = intent.getStringArrayListExtra("foodItemPrice") as ArrayList<String>
        foodItemDescription = intent.getStringArrayListExtra("foodItemDescription") as ArrayList<String>
        foodItemImage = intent.getStringArrayListExtra("foodItemImage") as ArrayList<String>
        foodItemTopping = intent.getStringArrayListExtra("foodItemTopping") as ArrayList<String>
        foodItemQuantity = intent.getStringArrayListExtra("foodItemQuantity") as ArrayList<Int>
        foodItemNote = intent.getIntegerArrayListExtra("foodItemNote") as ArrayList<String>

        totalAmount = calculateTotalAmount().toString() + " đ"
//        binding.editTotal.isEnabled = false
        binding.editTotal.setText(totalAmount)

        binding.btnPaymentAccept.setOnClickListener {
            //get data from text view
            name = binding.editName.text.toString().trim()
            address = binding.editAddr.text.toString().trim()
            phone = binding.editPhone.text.toString().trim()
            if (name.isBlank() && address.isBlank() && phone.isBlank()) {
                Toast.makeText(this, "Please Enter All Information!", Toast.LENGTH_SHORT).show()
            } else {
                placeOrder()
            }
        }
        binding.btnPaymentOut.setOnClickListener {
            finish()
        }
    }

    private fun placeOrder() {
        userID = sha1(auth.currentUser?.email?:"")
        val time = System.currentTimeMillis()
        val itemPushKey = database.child("OrderDetails").push().key
        val orderDetails = OrderDetail(userID, name, address, phone, foodItemName, foodItemPrice, foodItemImage, foodItemQuantity, foodItemNote, totalAmount, false, false, itemPushKey, time)
        val orderRef: DatabaseReference = database.child("OrderDetail").child(itemPushKey!!)
        orderRef.setValue(orderDetails).addOnSuccessListener {
            val intent = Intent(this, ConfirmOrder::class.java)
            startActivity(intent)

            removeItemFromCart()

            addOrderToHistory(orderDetails)
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to order!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addOrderToHistory(orderDetails: OrderDetail) {
        database.child("User").child(userID).child("BuyHistory")
            .child(orderDetails.itemPushKey!!).setValue(orderDetails).
            addOnSuccessListener {
                Toast.makeText(this, "Successfully to add buy history!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeItemFromCart() {
        userID = sha1(auth.currentUser?.email?:"")
        var cartRef:DatabaseReference = database.child("User").child(userID).child("Cart")
        cartRef.removeValue()
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in 0 until foodItemPrice.size){
            var price = foodItemPrice[i]
            val lastChar = price.last()
            val priceIntValue = if (lastChar == 'đ') {
                price.dropLast(2).toInt()
            } else {
                price.toInt()
            }
//            var quantity = foodItemQuantity[i]
//            totalAmount += priceIntValue * quantity
            totalAmount += priceIntValue
        }
        return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user != null) {
            userID = sha1(user.email?:"")
            val userRef = database.child("User").child(userID)
            userRef.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        name = snapshot.child("name").getValue(String::class.java)?:""
                        address = snapshot.child("address").getValue(String::class.java)?:""
                        phone = snapshot.child("phone").getValue(String::class.java)?:""
                        binding.apply {
                            editName.setText(name)
                            editAddr.setText(address)
                            editPhone.setText(phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }

    fun sha1(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-1").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}