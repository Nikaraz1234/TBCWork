package com.example.tbcworks.presentation.screens.messenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tbcworks.R
import com.example.tbcworks.data.models.MessageType
import com.example.tbcworks.data.models.User
import com.example.tbcworks.databinding.ItemUserLayoutBinding

class MessengerAdapter : ListAdapter<User, MessengerAdapter.MessengerViewHolder>(DIFFUTIL){

    inner class MessengerViewHolder(private val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) = with(binding){
            tvName.text = user.owner
            tvMessage.text = when (user.lastMessageType) {
                MessageType.TEXT -> user.lastMessage
                MessageType.FILE -> SENT_AN_ATTACHMENT
                MessageType.VOICE -> SENT_A_VOICE
            }
            if(user.unreadMessages != 0){
                tvUnreadMessages.visibility = View.VISIBLE
                tvUnreadMessages.text = user.unreadMessages.toString()
            }else{
                tvUnreadMessages.visibility = View.GONE
            }

            ivMessageIcon.apply {
                visibility = when (user.lastMessageType) {
                    MessageType.FILE -> View.VISIBLE
                    MessageType.VOICE -> View.VISIBLE
                    else -> View.GONE
                }
                if (visibility == View.VISIBLE) {
                    setImageResource(
                        when (user.lastMessageType) {
                            MessageType.FILE -> R.drawable.icon_file
                            MessageType.VOICE -> R.drawable.icon_voice
                            else -> 0
                        }
                    )
                }
            }



            tvTime.text = user.lastActive

            if (!user.image.isNullOrEmpty()) {
                ivUser.load(user.image) {
                    placeholder(R.drawable.icon_placeholder)
                    error(R.drawable.icon_placeholder)
                }
            } else {
                ivUser.setImageResource(R.drawable.icon_placeholder)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessengerAdapter.MessengerViewHolder {

        return MessengerViewHolder(ItemUserLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(
        holder: MessengerAdapter.MessengerViewHolder,
        position: Int
    ) {
        val user = getItem(position)
        holder.bind(user)

    }

    companion object  {
        val DIFFUTIL = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
        }

        const val SENT_AN_ATTACHMENT = "Sent an attachment"
        const val SENT_A_VOICE = "Sent a voice message"
    }

}