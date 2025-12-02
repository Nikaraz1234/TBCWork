package com.example.tbcworks.presentation.screens.lock_screen.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ItemDotLayoutBinding
import com.example.tbcworks.presentation.common.BaseDiffUtil
import com.example.tbcworks.presentation.screens.lock_screen.models.DotModel

class DotsAdapter :
    ListAdapter<DotModel, DotsAdapter.DotViewHolder>(
        BaseDiffUtil(
            areItemsTheSameCallback = { old, new -> old === new },
            areContentsTheSameCallback = { old, new -> old == new }
        )
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DotViewHolder {
        val binding = ItemDotLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DotViewHolder(binding)
    }

    inner class DotViewHolder(private val binding: ItemDotLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(item: DotModel) {
                val drawable = if (item.filled) {
                    R.drawable.shape_dot_filled
                } else {
                    R.drawable.shape_dot_empty
                }
                    binding.ivDot.setBackgroundResource(drawable)
                }
            }

    override fun onBindViewHolder(holder: DotViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



}