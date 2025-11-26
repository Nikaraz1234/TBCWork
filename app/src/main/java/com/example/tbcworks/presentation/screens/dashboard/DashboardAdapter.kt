package com.example.tbcworks.presentation.screens.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.data.auth.models.User
import com.example.tbcworks.databinding.ItemUserLayoutBinding

class DashboardAdapter :
    PagingDataAdapter<User, DashboardAdapter.DashboardViewHolder>(DIFFUTIL) {

    inner class DashboardViewHolder(private val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvId.text = user.id.toString()
            binding.tvEmail.text = user.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val binding = ItemUserLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
        }
    }
}
