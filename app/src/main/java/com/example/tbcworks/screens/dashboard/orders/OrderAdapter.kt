package com.example.tbcworks.screens.dashboard.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.OrderItemBinding

class OrderAdapter(
    private val onSubmitClicked: (Order) -> Unit
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
            tvName.text = order.name
            tvColorAndQuantity.text = order.colorName.plus(QUANTITY_TEXT).plus(order.quantity)
            imgItem.setImageResource(order.image)
            imgItem.setColorFilter(
                ContextCompat.getColor(imgColor.context, order.color),
                android.graphics.PorterDuff.Mode.SRC_IN
            )

            imgColor.backgroundTintList = ContextCompat.getColorStateList(imgColor.context, order.color)
            tvPrice.text = DOLLAR.plus(order.price.toString())
            if(order.status == OrderStatus.ACTIVE){
                tvIsCompleted.text = IN_DELIVERY
                btnSubmit.text = TRACK_ORDER

            }else{
                tvIsCompleted.text = COMPLETED
                if (order.reviews.isNotEmpty()) {
                    btnSubmit.text = BUY_AGAIN
                    btnSubmit.isEnabled = false
                }else{
                    btnSubmit.text = LEAVE_REVIEW
                    btnSubmit.isEnabled = true
                }
            }
            btnSubmit.setOnClickListener {
                onSubmitClicked(order)
            }
        }
        }



    companion object {
        const val BUY_AGAIN = "Buy Again"
        const val TRACK_ORDER = "Track order"
        const val LEAVE_REVIEW = "Leave Review"
        const val DOLLAR = "$"
        const val COMPLETED = "Completed"
        const val IN_DELIVERY = "In Delivery"
        const val QUANTITY_TEXT = "  |  Qty = "
        val DIFFUTIL = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem.id== newItem.id
            override fun areContentsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
        }
    }
}