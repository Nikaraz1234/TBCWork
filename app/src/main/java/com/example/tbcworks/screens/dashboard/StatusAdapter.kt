package com.example.tbcworks.screens.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.StatusItemBinding
import com.example.tbcworks.screens.dashboard.items.OrderStatus
import com.example.tbcworks.screens.dashboard.items.Status

class StatusAdapter(
    private val onStatusClick: (OrderStatus) -> Unit
) : ListAdapter<Status, StatusAdapter.StatusViewHolder>(DIFFUTIL)  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatusViewHolder {
        val binding = StatusItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatusViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: StatusViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class StatusViewHolder(private val binding: StatusItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(statusItem : Status) = with(binding){
            btnStatus.text = statusItem.status.toString()
            root.isSelected = statusItem.isSelected

            if(statusItem.isSelected){
                btnStatus.setBackgroundResource(R.drawable.selected_status_background)
                binding.btnStatus.setTextColor(android.graphics.Color.WHITE)
            }else{
                btnStatus.background = null
                binding.btnStatus.setTextColor(android.graphics.Color.BLACK)
            }

            btnStatus.setOnClickListener {
                onStatusClick(statusItem.status)
            }
        }

    }
    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<Status>() {
            override fun areItemsTheSame(oldItem: Status, newItem: Status) = oldItem.status== newItem.status
            override fun areContentsTheSame(oldItem: Status, newItem: Status) = oldItem == newItem
        }
    }
}