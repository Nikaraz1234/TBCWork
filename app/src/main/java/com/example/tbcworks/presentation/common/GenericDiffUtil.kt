package com.example.tbcworks.presentation.common

import androidx.recyclerview.widget.DiffUtil

class GenericDiffUtil<T>(
    private val areItemsSame: (T, T) -> Boolean,
    private val areContentsSame: (T, T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean = areItemsSame(oldItem, newItem)
    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean = areContentsSame(oldItem, newItem)
}
