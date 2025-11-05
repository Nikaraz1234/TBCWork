package com.example.tbcworks.screens.messenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.MessageItemLeftBinding
import com.example.tbcworks.databinding.MessageItemRightBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MessageAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(DIFFUTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_RIGHT) {
            val binding = MessageItemRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            RightViewHolder(binding)
        } else {
            val binding = MessageItemLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LeftViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (holder) {
            is RightViewHolder -> holder.bind(message)
            is LeftViewHolder -> holder.bind(message)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).id % 2 == 0) TYPE_RIGHT else TYPE_LEFT
    }
    inner class LeftViewHolder(private val binding: MessageItemLeftBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(message: Message) {
            with(binding) {
                tvMessage.text = message.content
                val time =  formatMessageTime(message.sentTime)
                tvTime.text = time
            }
        }
        }

    inner class RightViewHolder(private val binding: MessageItemRightBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(message: Message) {
            with(binding){
                tvMessage.text = message.content
                val time =  formatMessageTime(message.sentTime)
                tvTime.text = time
            }
        }
        }

    private fun formatMessageTime(sentTime: LocalDateTime): String {
        val now = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        return when {
            sentTime.toLocalDate() == now.toLocalDate() ->
                "Today ${sentTime.format(timeFormatter)}"
            sentTime.toLocalDate() == now.minusDays(1).toLocalDate() ->
                "Yesterday ${sentTime.format(timeFormatter)}"
            else ->
                sentTime.format(dateTimeFormatter)
        }
    }



    companion object {
        private const val TYPE_RIGHT = 0
        private const val TYPE_LEFT = 1

        val DIFFUTIL = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id== newItem.id
            override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
        }
    }
}
