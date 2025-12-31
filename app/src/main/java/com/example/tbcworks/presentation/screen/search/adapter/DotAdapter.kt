package com.example.tbcworks.presentation.screen.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.presentation.common.GenericDiffCallback

class DotAdapter(dotsCount: Int) :
    ListAdapter<Int, DotAdapter.DotViewHolder>(
        GenericDiffCallback(
            areItemsTheSameCheck = { old, new -> old == new },
            areContentsTheSameCheck = { old, new -> old == new }
        )
    ) {

    init {
        val items = List(dotsCount) { it }
        submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dot, parent, false)
        return DotViewHolder(view)
    }

    override fun onBindViewHolder(holder: DotViewHolder, position: Int) {
    }

    class DotViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
