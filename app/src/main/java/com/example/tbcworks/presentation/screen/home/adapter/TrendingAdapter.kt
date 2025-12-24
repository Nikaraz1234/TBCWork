package com.example.tbcworks.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ItemTrendingEventBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.model.EventModel
import com.example.tbcworks.presentation.screen.home.mapper.toDisplayDate

class TrendingAdapter(
    private val onEventClick: (EventModel) -> Unit
) : ListAdapter<EventModel, TrendingAdapter.ViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new ->
            old.title == new.title &&
                    old.date == new.date
        },
        areContentsTheSameCheck = { old, new ->
            old == new
        }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrendingEventBinding.inflate(
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
        private val binding: ItemTrendingEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventModel) = with(binding) {

            tvEventTitle.text = event.title

            tvEventDate.text = event.date.toDisplayDate()

            ivEventImage.load(event.imgUrl) {
                placeholder(R.drawable.shape_btn)
                error(R.drawable.shape_btn)
                crossfade(true)
            }

            root.setOnClickListener {
                onEventClick(event)
            }
        }
    }
}
