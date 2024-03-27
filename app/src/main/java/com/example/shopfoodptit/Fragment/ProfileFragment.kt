package com.example.shopfoodptit.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.shopfoodptit.R
import com.example.shopfoodptit.databinding.FragmentProfileBinding
import com.example.shopfoodptit.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.security.MessageDigest

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        setUserData()

        binding.btnSaveInforCustomer.setOnClickListener{
            val name = binding.profileName.text.toString()
            val email = binding.profileEmail.text.toString()
            val address = binding.profileAddress.text.toString()
            val phone = binding.profilePhone.text.toString()
            val pass = binding.profilePass.text.toString()
            updateUserData(name, email, address, phone, pass)
        }

        return binding.root
    }

    private fun updateUserData(name: String, email: String, address: String, phone: String, pass: String) {
        val userID = sha1(auth.currentUser?.email?:"")
        if (userID != null) {
            val userRef:DatabaseReference = database.getReference().child("User").child(userID)
            val userData = hashMapOf(
                "name" to name,
                "email" to email,
                "address" to address,
                "phone" to phone,
                "password" to pass
            )
            userRef.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(requireContext(), "Profile Updated Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData() {
        val userID = sha1(auth.currentUser?.email?:"")
        if (userID != null) {
            val userRef: DatabaseReference = database.getReference().child("User").child(userID)
            userRef.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userProfile = snapshot.getValue(UserModel::class.java)
                    if (userProfile != null) {
                        binding.profileName.setText(userProfile.name)
                        binding.profileEmail.setText(userProfile.email)
                        binding.profileAddress.setText(userProfile.address)
                        binding.profilePhone.setText(userProfile.phone)
                        binding.profilePass.setText(userProfile.password)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    fun sha1(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-1").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}