package com.example.tbcworks.presentation.common

import androidx.recyclerview.widget.DiffUtil

class GenericDiffCallback<T : Any>(
    private val areItemsTheSameCheck: (T, T) -> Boolean,
    private val areContentsTheSameCheck: (T, T) -> Boolean
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSameCheck(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSameCheck(oldItem, newItem)
}
