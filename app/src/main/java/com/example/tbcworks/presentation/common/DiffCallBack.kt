package com.example.tbcworks.presentation.common

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcworks.presentation.screens.home.LocationModel

class DiffCallback : DiffUtil.ItemCallback<LocationModel>() {
    override fun areItemsTheSame(oldItem: LocationModel, newItem: LocationModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: LocationModel, newItem: LocationModel): Boolean {
        return oldItem == newItem
    }
}