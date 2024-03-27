package com.example.shopfoodptit.model

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class OrderDetail():Parcelable {
    var userId: String? = null
    var userName: String? = null
    var address: String? = null
    var phone: String? = null
    var foodNames: MutableList<String>? = null
    var foodImages: MutableList<String>? = null
    var foodPrices: MutableList<String>? = null
    var foodQuantities: MutableList<Int>? = null
    var foodNotes: MutableList<String>? = null
    var totalPrice: String? = null
    var orderAccepted: Boolean? = false
    var paymentReceived: Boolean? = false
    var itemPushKey: String? = null
    var currentTime: Long = 0

    constructor(parcel: Parcel) : this() {
        userId = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        phone = parcel.readString()
        totalPrice = parcel.readString()
        orderAccepted = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        paymentReceived = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    constructor(
        userID: String,
        name: String,
        address: String,
        phone: String,
        foodItemName: ArrayList<String>,
        foodItemPrice: ArrayList<String>,
        foodItemImage: ArrayList<String>,
        foodItemQuantity: ArrayList<Int>,
        foodItemNote: ArrayList<String>,
        totalAmount: String,
        b: Boolean,
        b1: Boolean,
        itemPushKey: String?,
        time: Long
    ) : this() {
        this.userId = userID
        this.userName = name
        this.address = address
        this.phone = phone
        this.foodNames = foodItemName
        this.foodPrices = foodItemPrice
        this.foodImages = foodItemImage
        this.foodQuantities = foodItemQuantity
        this.foodNotes = foodItemNote
        this.totalPrice = totalAmount
        this.orderAccepted = orderAccepted
        this.paymentReceived = paymentReceived
        this.itemPushKey = itemPushKey
        this.currentTime = time
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(totalPrice)
        parcel.writeValue(orderAccepted)
        parcel.writeValue(paymentReceived)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetail> {
        override fun createFromParcel(parcel: Parcel): OrderDetail {
            return OrderDetail(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetail?> {
            return arrayOfNulls(size)
        }
    }
}
