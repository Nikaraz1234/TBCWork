package com.example.tbcworks.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ItemEventCategoryBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.screen.home.model.CategoryModel

class CategoryAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<CategoryModel, CategoryAdapter.ViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new ->
            old.category == new.category
        },
        areContentsTheSameCheck = { old, new ->
            old == new
        }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemEventCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryModel) = with(binding) {
            //ivLogo.setImageResource(item.iconRes)
            tvCategory.text = item.category
            tvCount.text = "${item.eventCount} Events"

            root.setOnClickListener {
                onClick(item.category)
            }
        }
    }
}
