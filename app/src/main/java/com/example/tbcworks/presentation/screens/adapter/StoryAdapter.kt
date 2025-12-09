package com.example.tbcworks.presentation.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ItemStoryLayoutBinding
import com.example.tbcworks.presentation.screens.model.StoryModel

class StoryAdapter :
    ListAdapter<StoryModel, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    inner class StoryViewHolder(
        private val binding: ItemStoryLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StoryModel) = with(binding) {
            textTitle.text = item.title
            imageBackground.load(item.cover) {
                crossfade(true)
                placeholder(R.drawable.bg_edit_text)
                error(R.drawable.bg_edit_text)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryModel>() {
            override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean =
                oldItem == newItem
        }
    }

}
