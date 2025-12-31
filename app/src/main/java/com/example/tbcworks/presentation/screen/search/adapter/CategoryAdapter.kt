package com.example.tbcworks.presentation.screen.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ItemSearchBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.screen.search.model.CategoryModel

class CategoryAdapter(
    private val onItemClick: (CategoryModel) -> Unit
) :
    ListAdapter<CategoryModel, CategoryAdapter.CategoryViewHolder>(
        GenericDiffCallback(
            areItemsTheSameCheck = { old, new -> old.id == new.id },
            areContentsTheSameCheck = { old, new -> old == new }
        )
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
    class CategoryViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryModel) {
            binding.tvName.text = item.title

            binding.rvDots.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvDots.adapter = DotAdapter(item.depth)
        }
    }
}
