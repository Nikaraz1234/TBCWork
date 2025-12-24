package com.example.tbcworks.presentation.screen.event_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ItemAgendaBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.model.AgendaModel
import com.example.tbcworks.presentation.screen.home.mapper.toFormattedTime

class AgendaAdapter :
    ListAdapter<AgendaModel, AgendaAdapter.AgendaViewHolder>(
        GenericDiffCallback(
            areItemsTheSameCheck = { old, new ->
                old.startTime == new.startTime &&
                        old.title == new.title
            },
            areContentsTheSameCheck = { old, new ->
                old == new
            }
        )
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAgendaBinding.inflate(inflater, parent, false)
        return AgendaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AgendaViewHolder, position: Int) {
        holder.bind(getItem(position), position, itemCount)
    }

    inner class AgendaViewHolder(
        private val binding: ItemAgendaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: AgendaModel,
            position: Int,
            itemCount: Int
        ) = with(binding) {

            // Number circle (1-based index)
            numberCircle.text = (position + 1).toString()

            // Time text: "02:00 PM - 45 min"
            timeText.text = "${item.startTime.toFormattedTime()} - ${item.duration}"


            // Title
            titleText.text = item.title

            // Description
            descriptionText.text = item.description

            // Hide separator for last item
            separator1.isVisible = position != itemCount - 1
        }
    }
}