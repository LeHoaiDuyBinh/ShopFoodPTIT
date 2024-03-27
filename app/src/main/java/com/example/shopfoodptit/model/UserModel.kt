package com.example.shopfoodptit.model

import android.provider.ContactsContract.CommonDataKinds.Phone

data class UserModel(
    val name: String?= null,
    val email: String?= null,
    val address: String?= null,
    val phone: String?= null,
    val password: String?= null
)
