package com.example.tbcworks.screens.dashboard.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.tbcworks.databinding.FragmentOrderDetailsBottomSheetBinding
import com.example.tbcworks.screens.dashboard.orders.OrderAdapter.Companion.COMPLETED
import com.example.tbcworks.screens.dashboard.orders.OrderAdapter.Companion.DOLLAR
import com.example.tbcworks.screens.dashboard.orders.OrderAdapter.Companion.IN_DELIVERY
import com.example.tbcworks.screens.dashboard.orders.OrderAdapter.Companion.LEAVE_REVIEW
import com.example.tbcworks.screens.dashboard.orders.OrderAdapter.Companion.QUANTITY_TEXT
import com.example.tbcworks.screens.dashboard.orders.OrderAdapter.Companion.TRACK_ORDER
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class OrderDetailsBottomSheet(
    private val order: Order,
    private val onReviewSubmitted: (String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentOrderDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBottomSheetBinding.inflate(inflater, container, false)

        with(binding){
            tvName.text = order.name
            tvColorAndQuantity.text = order.colorName.plus(QUANTITY_TEXT).plus(order.quantity)
            imgItem.setImageResource(order.image)
            tvPrice.text = DOLLAR.plus(order.price.toString())
            if(order.status == OrderStatus.ACTIVE){
                tvIsCompleted.text = IN_DELIVERY

            }else{
                tvIsCompleted.text = COMPLETED
            }
            imgColor.backgroundTintList = ContextCompat.getColorStateList(imgColor.context, order.color)
            btnSubmit.setOnClickListener {
                val review = etReview.text.toString().trim()
                if (review.isNotEmpty()) {
                    onReviewSubmitted(review)
                    dismiss()
                }else{
                    Snackbar.make(root, REVIEW_ERROR, Snackbar.LENGTH_SHORT).show()

                }
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.let { bottomSheet ->
            bottomSheet.layoutParams.height = (resources.displayMetrics.heightPixels * 0.75).toInt()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        const val REVIEW_ERROR = "Review can't be empty"
    }
}