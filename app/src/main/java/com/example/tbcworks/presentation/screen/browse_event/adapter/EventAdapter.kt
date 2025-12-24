package com.example.tbcworks.presentation.screen.browse_event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ItemFilterEventBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.screen.home.mapper.day
import com.example.tbcworks.presentation.screen.home.mapper.monthShort
import com.example.tbcworks.presentation.model.EventModel
import com.example.tbcworks.presentation.screen.home.mapper.toTimeRange


class EventAdapter(
    private val onItemClick: (EventModel) -> Unit
) : ListAdapter<EventModel, EventAdapter.EventViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new -> old.id == new.id },
        areContentsTheSameCheck = { old, new -> old == new }
    )
) {

    inner class EventViewHolder(private val binding: ItemFilterEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventModel) {

            binding.tvMonth.text = event.date.monthShort()
            binding.tvDay.text = event.date.day()

            // Event info
            binding.tvCategory.text = event.category
            binding.tvStatus.text = event.registrationStatus.name
            binding.tvTitle.text = event.title
            binding.tvTime.text = event.date.toTimeRange()
            binding.tvLocation.text = event.location.venueName
            binding.tvRegistered.text = "${event.capacity.currentlyRegistered} registered"
            binding.tvSpotsLeft.text = "${event.capacity.maxCapacity - event.capacity.currentlyRegistered} spots left"

            // Click listener
            binding.root.setOnClickListener {
                onItemClick(event)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemFilterEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
