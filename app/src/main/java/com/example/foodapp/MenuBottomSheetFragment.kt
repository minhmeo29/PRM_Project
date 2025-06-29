package com.example.foodapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodapp.adapter.MenuAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.ArrayList

class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        val cmenuFoodName=listOf("Burger", "Sandwich", "Momo",
            "Item", "Momo", "Sandwich")

        val cmenuItemPrice = listOf(
            "10,000VND",
            "20,000VND",
            "30,000VND",
            "10,000VND",
            "20,000VND",
            "30,000VND"
        )
        val cmenuImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6
        )

        val adapter = MenuAdapter(
            ArrayList(cmenuFoodName),
            ArrayList(cmenuItemPrice),
            ArrayList(cmenuImage),
            requireContext()
        )

        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.setAdapter(adapter)

        return binding.root
    }

    class Companion
}