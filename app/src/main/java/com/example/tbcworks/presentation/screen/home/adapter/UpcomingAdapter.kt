package com.example.tbcworks.presentation.screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ItemUpcomingEventBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.screen.mapper.day
import com.example.tbcworks.presentation.screen.mapper.monthShort
import com.example.tbcworks.presentation.screen.mapper.toTimeRange
import com.example.tbcworks.presentation.screen.model.EventModel

class UpcomingAdapter(
    private val onEventClick: (EventModel) -> Unit
) : ListAdapter<EventModel, UpcomingAdapter.ViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new ->
            old.title == new.title && old.date == new.date
        },
        areContentsTheSameCheck = { old, new ->
            old == new
        }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUpcomingEventBinding.inflate(
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
        private val binding: ItemUpcomingEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventModel) = with(binding) {

            // Date
            tvMonth.text = event.date.monthShort()
            tvDay.text = event.date.day().toString()

            // Main info
            tvTitle.text = event.title
            tvDescription.text = event.description
            tvLocation.text = event.location.venueName

            // Time
            tvTime.text = event.date.toTimeRange()

            // Capacity info
            tvRegistered.text = event.capacity.currentlyRegistered.toString()

            root.setOnClickListener {
                onEventClick(event)
            }
        }
    }
}
