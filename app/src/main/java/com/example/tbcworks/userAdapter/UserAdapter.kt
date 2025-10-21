package com.example.tbcworks.userAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.UserItemLayoutBinding
import com.example.tbcworks.userModel.User


class UserAdapter(private var users: List<User>, private var updateOnLongClick: (User) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: UserItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int){
       val user = users[position]
        with(holder.binding){
            tvName.text = root.context.getString(R.string.name, user.firstName, user.lastName)
            tvEmail.text = root.context.getString(R.string.email_, user.email)
            tvAge.text = root.context.getString(R.string.age_, user.age.toString())

            root.setOnLongClickListener {
                updateOnLongClick(user)
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int = users.size

    fun updateUsers(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
}