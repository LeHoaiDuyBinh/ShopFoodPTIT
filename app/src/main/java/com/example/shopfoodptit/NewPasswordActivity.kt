package com.example.shopfoodptit

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shopfoodptit.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.security.MessageDigest

class NewPasswordActivity : AppCompatActivity() {
    companion object {
        const val EMAIL_KEY = "email"
    }

    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var btnConfirmPassword: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private var email: String? = null
    private var userID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        firebaseAuth = FirebaseAuth.getInstance()

        email = intent.getStringExtra(EMAIL_KEY)

        newPasswordEditText = findViewById(R.id.edt_new_password)
        confirmPasswordEditText = findViewById(R.id.edt_confirm_password)
        btnConfirmPassword = findViewById(R.id.btn_confirm_password)

        btnConfirmPassword.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val sanitizedEmail = sha1(email.toString())

            val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("User")
            val emailToQuery = email

            val query = FirebaseDatabase.getInstance().getReference("User")
                .orderByChild("email")
                .equalTo(emailToQuery)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val userID = userSnapshot.key // Lấy userID của người dùng
                            val user = userSnapshot.getValue(UserModel::class.java)
                            Log.d("UserID", userID ?: "User ID is null")
                            Log.d("UserData", user.toString())
                        }
                    } else {
                        Log.d("Firebase", "No user found with the specified email")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Xử lý khi truy vấn bị hủy bỏ
                    Log.e("Firebase", "Error: ${databaseError.message}")
                }
            })

            // Cập nhật mật khẩu mới của người dùng
            if (newPassword.isNotEmpty() && newPassword == confirmPassword) {
                email?.let { it1 ->
                    if (sanitizedEmail != null) {
                        reference.child(sanitizedEmail).child("password").setValue(newPassword)
                            .addOnSuccessListener {
                                // Xử lý khi cập nhật mật khẩu trong Database thành công
                                Toast.makeText(
                                    this,
                                    "Cập nhật mật khẩu thành công và vui lòng xác thực 1 lần nữa trên email của bạn",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener { exception ->
                                // Xử lý khi cập nhật mật khẩu trong Database thất bại
                                Toast.makeText(
                                    this,
                                    "Lỗi khi cập nhật mật khẩu trong Database: $exception",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        firebaseAuth.sendPasswordResetEmail(email.toString())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("Firebase", "Password reset email sent successfully")
                                    Toast.makeText(
                                        this@NewPasswordActivity,
                                        "Một email đặt lại mật khẩu đã được gửi đến $email",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(this, UpdatePasswordSuccess::class.java)
                                    startActivity(intent)
                                    // Tiếp tục với quá trình đặt lại mật khẩu trong trường hợp người dùng xác nhận email đặt lại mật khẩu
                                } else {
                                    Log.e("Firebase", "Failed to send password reset email: ${task.exception}")
                                    Toast.makeText(
                                        this@NewPasswordActivity,
                                        "Gửi email đặt lại mật khẩu không thành công: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
            }
        }
    }
    fun sha1(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-1").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
