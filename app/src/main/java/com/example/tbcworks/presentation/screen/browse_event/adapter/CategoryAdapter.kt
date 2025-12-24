package com.example.tbcworks.presentation.screen.browse_event.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ItemFilterCategoryBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback

class CategoryAdapter(
    private val onItemClick: (Int) -> Unit
) : ListAdapter<String, CategoryAdapter.CategoryViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new -> old == new },
        areContentsTheSameCheck = { old, new -> old == new }
    )
) {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class CategoryViewHolder(private val binding: ItemFilterCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String, position: Int) {
            binding.tvCategory.text = category

            binding.tvCategory.apply {
                setBackgroundResource(
                    if (position == selectedPosition)
                        R.drawable.shape_btn_black_rounded
                    else
                        R.drawable.shape_btn_gray_rounded
                )
                setTextColor(
                    if (position == selectedPosition)
                        context.getColor(R.color.white)
                    else
                        context.getColor(R.color.black)
                )
            }
            binding.root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemFilterCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}
