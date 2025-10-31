package com.example.tbcworks.screens.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.OrderItemBinding
import com.example.tbcworks.screens.dashboard.items.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class OrderAdapter(
    private val onDetailClicked: (Order) -> Unit
) : ListAdapter<Order, OrderAdapter.OrderViewHolder>(DIFFUTIL) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OrderViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(order: Order) = with(binding){
            tvOrderNumber.text = ORDER.plus(order.orderNumber.toString())
            tvTrackNumberValue.text = order.trackingNumber
            tvQuantityValue.text = order.quantity.toString()
            tvSubTotalValue.text = DOLLAR.plus(order.price.toString())
            val date = millisToDate(order.dateMillis)
            tvDate.text = date

            tvStatus.text = order.status.toString()

            val status = tvStatus.text.toString().lowercase().trim()

            when (status){
                PENDING -> tvStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.pending))
                DELIVERED -> tvStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.delivered))
                CANCELED -> tvStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.canceled))
            }
            tvStatus.text = order.status.toString()

            btnDetails.setOnClickListener {
                onDetailClicked(order)
            }
        }
        }


    private fun millisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }
    companion object {
        const val ORDER = "Order: #"
        const val DOLLAR = "$"
        const val DELIVERED = "delivered"
        const val PENDING = "pending"
        const val CANCELED = "canceled"
        val DIFFUTIL = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem.id== newItem.id
            override fun areContentsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
        }
    }
}