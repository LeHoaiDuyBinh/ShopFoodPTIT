package com.example.shopfoodptit

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import java.util.Properties
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class ForgotPasswordActivity : AppCompatActivity() {

    companion object {
        const val EMAIL = "ptithcmnckh@gmail.com"
        const val PASSWORD = "ivjp dzpp xroc hihc"
        const val RANDOM_NUMBER_KEY = "random_number"
        const val EMAIL_KEY = "email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnSendCode = findViewById<Button>(R.id.btn_send_code)
        val edtEmailForgotPassword = findViewById<EditText>(R.id.edt_email_forgot)

        btnSendCode.setOnClickListener {
            val email = edtEmailForgotPassword.text.toString()
            val randomNumber = (10000..99999).random().toString() // Sinh số ngẫu nhiên 5 chữ số

            sendEmail(email, randomNumber)
        }
    }

    private fun sendEmail(recipientEmail: String, randomNumber: String) {
        val properties = Properties()
        properties.put("mail.smtp.auth", "true")
        properties.put("mail.smtp.starttls.enable", "true")
        properties.put("mail.smtp.host", "smtp.gmail.com") // Thay thế smtp.example.com bằng SMTP server của bạn
        properties.put("mail.smtp.port", "587") // Thay thế port nếu cần

        val session = Session.getInstance(properties, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
                return javax.mail.PasswordAuthentication(EMAIL, PASSWORD)
            }
        })

        Thread {
            try {
                val message = MimeMessage(session)
                message.setFrom(InternetAddress(EMAIL))
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail))
                message.subject = "Reset Password"
                message.setText("Your verification code is: $randomNumber")

                Transport.send(message)

                runOnUiThread {
                    // Email đã được gửi thành công, bạn có thể thực hiện các hành động tiếp theo ở đây
                    val intent = Intent(this@ForgotPasswordActivity, Confirm_OTPActivity::class.java)
                    intent.putExtra(RANDOM_NUMBER_KEY, randomNumber)
                    intent.putExtra(EMAIL_KEY, recipientEmail)
                    startActivity(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    // Xử lý khi gửi email thất bại
                    // Ví dụ: Hiển thị thông báo lỗi cho người dùng
                }
            }
        }.start()
    }

}


