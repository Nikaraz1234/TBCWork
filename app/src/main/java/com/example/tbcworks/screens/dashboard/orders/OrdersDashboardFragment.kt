package com.example.tbcworks.screens.dashboard.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.R
import com.example.tbcworks.databinding.FragmentOrdersDashboardBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrdersDashboardFragment : BaseFragment<FragmentOrdersDashboardBinding>() {

    private val viewModel: OrderViewModel by viewModels()
    private lateinit var adapter: OrderAdapter
    private var isActive = true

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentOrdersDashboardBinding.inflate(inflater, container, false)

    override fun bind() {
        with(binding) {

            adapter = OrderAdapter { order ->
                val bottomSheet = OrderDetailsBottomSheet(order) { review ->

                    viewModel.addReview(review, order.id)
                }
                if(!isActive){
                    bottomSheet.show(parentFragmentManager, "OrderDetails")
                }

            }

            rvOrders.layoutManager = LinearLayoutManager(requireContext())
            rvOrders.adapter = adapter

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.filteredOrders.collectLatest { orders ->
                        adapter.submitList(orders)
                    }
                }
            }

            btnActive.setOnClickListener { showActiveOrders() }
            btnCompleted.setOnClickListener { showCompletedOrders() }

            val gestureDetector = android.view.GestureDetector(requireContext(),
                object : android.view.GestureDetector.SimpleOnGestureListener() {
                    override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                        if (e1 == null) return false

                        val diffX = e2.x - e1.x
                        if (diffX > 0) {
                            if (!isActive) showActiveOrders()
                        } else {
                            if (isActive) showCompletedOrders()
                        }
                        return true
                    }
                })
            rvOrders.setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                false
            }
        }
    }

    private fun showActiveOrders() =with(binding){
        isActive = true
        viewModel.filterOrders(OrderStatus.ACTIVE)
        btnActive.isSelected = true
        binding.btnCompleted.isSelected = false
        underlineActive.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        underlineCompleted.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
    }

    private fun showCompletedOrders() = with(binding){
        isActive = false
        viewModel.filterOrders(OrderStatus.COMPLETED)
        btnActive.isSelected = false
        btnCompleted.isSelected = true
        underlineActive.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        underlineCompleted.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
    }
}
