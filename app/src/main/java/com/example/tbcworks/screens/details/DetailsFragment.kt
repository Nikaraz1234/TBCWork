package com.example.tbcworks.screens.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.R
import com.example.tbcworks.databinding.FragmentDetailsBinding
import com.example.tbcworks.databinding.FragmentOrdersDashboardBinding
import com.example.tbcworks.screens.dashboard.items.OrderStatus


class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {
    private val args: DetailsFragmentArgs by navArgs()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding {
        return FragmentDetailsBinding.inflate(inflater, container, false)
    }

    override fun bind(){
        with(binding){
            val order = args.order
            order?.let {
                tvTitle.text = ORDER_TITLE.plus(order.orderNumber)
                tvOrderNumber.text = ORDER_NUMBER_TEXT.plus(order.orderNumber)
                tvTrackingNumber.text = TRACKING_NUMBER_TEXT.plus(order.trackingNumber)
                tvQuantity.text = QUANTITY_TEXT.plus(order.quantity)
                tvPrice.text = TOTAL_PRICE_TEXT.plus(order.price)
                tvStatus.text = STATUS_TEXT.plus(order.status)
                tvChangeStatus.text = CHANGE_STATUS_TEXT


                if(order.status.toString().lowercase().trim() != STATUS_PENDING){
                    spinnerStatusChanger.visibility = View.GONE
                    tvChangeStatus.visibility = View.GONE
                }

                btnSubmit.setOnClickListener {
                   if (tvChangeStatus.visibility != View.GONE){
                       val selectedStatus = spinnerStatusChanger.selectedItem?.toString()?.trim()
                       when (selectedStatus){
                           STATUS_DELIVERED -> order.status = OrderStatus.DELIVERED
                           STATUS_CANCELED -> order.status = OrderStatus.CANCELED
                           else -> order.status = OrderStatus.PENDING
                       }
                   }
                    val bundle = Bundle().apply {
                        putSerializable(ORDER_UPDATED_KEY, order)
                    }
                    setFragmentResult(UPDATED_ORDER_KEY, bundle)
                    findNavController().popBackStack()
                }
            }

        }
    }


    companion object{
        const val ORDER_TITLE = "Order #"
        const val ORDER_NUMBER_TEXT = "Order Number: "
        const val TRACKING_NUMBER_TEXT = "Tracking Number: "
        const val QUANTITY_TEXT = "Quantity: "
        const val TOTAL_PRICE_TEXT = "Total Price: $"
        const val STATUS_TEXT = "Status: "
        const val CHANGE_STATUS_TEXT = "Change Status: "
        const val STATUS_DELIVERED = "Delivered"
        const val STATUS_CANCELED = "Canceled"
        const val STATUS_PENDING = "pending"
        const val ORDER_UPDATED_KEY = "orderUpdated"
        const val UPDATED_ORDER_KEY = "updatedOrder"

    }
}