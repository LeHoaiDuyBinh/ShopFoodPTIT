package com.example.shopfoodptit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.shopfoodptit.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import android.util.Base64
import android.util.Log
import android.view.View
import java.security.MessageDigest

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var userName : String
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var database : DatabaseReference
    private lateinit var edtName: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        khoiTao()

        auth = Firebase.auth
        database = Firebase.database.reference

        val btnRegister = findViewById<Button>(R.id.btn_sign_up)

        btnRegister.setOnClickListener{
            email = edtEmail.text.toString().trim()
            userName = edtName.text.toString().trim()
            password = edtPassword.text.toString().trim()
            var s : String = "@ptitfoodadmin.edu.vn"
            var tvCheck : TextView = findViewById(R.id.tv_username_error_signUp)
            var tvCheckPass : TextView = findViewById(R.id.tv_password_error_signUp)

            if (email.isBlank() || userName.isBlank() || password.isBlank()){
                Toast.makeText(this, "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show()
            }else{
                if (!email.endsWith(s)){
                    tvCheck.visibility = View.GONE
                    if (password.length > 5) {
                        tvCheckPass.visibility = View.GONE
                        registerUser(email, password)
                    }else{
                        tvCheckPass.visibility = View.VISIBLE
                    }
                }else{
                    tvCheck.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun khoiTao() {
        edtName = findViewById(R.id.edt_name)
        edtPassword = findViewById(R.id.edt_password)
        edtEmail = findViewById(R.id.edt_email)
    }

    private fun registerUser(email: String, password : String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Đã đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show()
                saveUser()
                val intent = Intent(this, LoginUserActivity ::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Đã xảy ra lỗi khi đăng ký", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUser() {
        email = edtEmail.text.toString().trim()
        userName = edtName.text.toString().trim()
        password = edtPassword.text.toString().trim()
        val user = UserModel(userName, email, password)
        // Lưu thông tin khách hàng vào database của Firebase realtime
//        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val encodedEmail = sha1(email)
        Log.d("dumamay", encodedEmail)

        database.child("User").child(encodedEmail).setValue(user)
    }

    fun sha1(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-1").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

}