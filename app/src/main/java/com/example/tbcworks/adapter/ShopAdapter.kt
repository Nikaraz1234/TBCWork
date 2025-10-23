package com.example.tbcworks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ShopItemLayoutBinding
import com.example.tbcworks.items.ShopItem

class ShopAdapter : ListAdapter<ShopItem, ShopAdapter.ShopViewHolder>(
    object : DiffUtil.ItemCallback<ShopItem>() {
        override fun areItemsTheSame(
            oldItem: ShopItem,
            newItem: ShopItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShopItem,
            newItem: ShopItem
        ): Boolean {
            return oldItem == newItem
        }

    }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopAdapter.ShopViewHolder {
        val binding = ShopItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding){
            tvTitle.text = item.title
            tvPrice.text = "${item.price}$"
            ivHumanImage.setImageResource(item.image)
        }
    }

    inner class ShopViewHolder(val binding: ShopItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


}