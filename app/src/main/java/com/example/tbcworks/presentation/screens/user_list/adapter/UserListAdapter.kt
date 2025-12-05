package com.example.tbcworks.presentation.screens.user_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ItemUserLayoutBinding
import com.example.tbcworks.presentation.screens.user_list.UserModel

class UserListAdapter :
    ListAdapter<UserModel, UserListAdapter.UserViewHolder>(DIFF_CALLBACK) {

    inner class UserViewHolder(private val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserModel) = with(binding){
            userName.text = user.fullName
            userEmail.text = user.email

            userStatus.text = user.lastActiveDescription

            val imageUrl = user.profileImageUrl ?: user.cachedImagePath
            userImage.load(imageUrl) {
                placeholder(R.drawable.ic_profile_placeholder)
                error(R.drawable.ic_profile_error)
                crossfade(true)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(
                oldItem: UserModel,
                newItem: UserModel
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UserModel,
                newItem: UserModel
            ) = oldItem == newItem
        }
        private const val ONLINE = "Online"
    }
}
