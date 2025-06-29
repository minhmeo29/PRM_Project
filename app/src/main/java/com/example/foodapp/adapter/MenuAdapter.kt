package com.example.foodapp.adapter

import android.content.Context
import android.content.Intent
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.DetailActivity
import com.example.foodapp.adapter.MenuAdapter.MenuViewHolder
import com.example.foodapp.databinding.MenuItemBinding

class MenuAdapter(
    private val menuItemsName: List<String>,
    private val menuItemPrice: List<String>,
    private val menuImage: List<Int>,
    private val requireContext: Context // <-- Thêm dòng này
) :
    RecyclerView.Adapter<MenuViewHolder>() {

    private val itemClickListener : OnClickListener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return menuItemsName.size
    }

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener()
                {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION)
                    {
                        itemClickListener?.onItemClick(position)
                    }
                    // Set OnClick Listener to open details
                    val intent =  Intent(requireContext, DetailActivity::class.java)
                    intent.putExtra("MenuItemName", menuItemsName.get(position))
                    intent.putExtra("MenuItemImage", menuImage.get(position))
                    requireContext.startActivity(intent)
                }
            }

        fun bind(position: Int) {
            binding.menuFoodName.text = menuItemsName[position]
            binding.menuPrice.text = menuItemPrice[position]
            binding.menuImage.setImageResource(menuImage[position])


        }
    }
    interface OnClickListener {
        fun onItemClick(position: Int)
        // TODO: ("Not Yet Implemented")
    }
}

interface OnClickListener {
    fun onItemClick(position: Int)
    // TODO: ("Not Yet Implemented")
}

