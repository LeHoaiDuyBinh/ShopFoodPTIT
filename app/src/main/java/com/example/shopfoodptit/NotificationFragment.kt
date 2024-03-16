package com.example.shopfoodptit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopfoodptit.adapter.NotificationAdapter
import com.example.shopfoodptit.databinding.FragmentNotificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotificationFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)
        val notifications = listOf("Đã hủy đơn hàng", "Đơn hàng đang xử lý", "Đơn hàng đặt thành công")
        val notificationImgs = listOf(R.drawable.sademoji, R.drawable.truck, R.drawable.congrats)
        val adapter = NotificationAdapter(
            ArrayList(notifications),
            ArrayList(notificationImgs)
        )
        binding.revNotification.layoutManager = LinearLayoutManager(requireContext())
        binding.revNotification.adapter = adapter
        return binding.root
    }

    companion object {

    }
}