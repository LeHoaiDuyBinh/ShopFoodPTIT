package com.example.shopfoodptit.model

data class CartItem(
    val foodName : String?=null,
    val foodPrice : String?=null,
    val foodDescription : String?=null,
    val foodImage : String?=null,
    val foodTopping : String?=null,
    val foodQuantity : Int?=null
)
