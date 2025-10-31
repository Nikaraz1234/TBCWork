package com.example.tbcworks.screens.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.databinding.FragmentOrdersDashboardBinding
import com.example.tbcworks.screens.dashboard.items.Order
import com.example.tbcworks.screens.dashboard.viewModel.DashboardViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrdersDashboardFragment : BaseFragment<FragmentOrdersDashboardBinding>() {

    private val viewModel : DashboardViewModel by viewModels()
    private lateinit var statusAdapter: StatusAdapter
    private lateinit var orderAdapter: OrderAdapter


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrdersDashboardBinding {
        return FragmentOrdersDashboardBinding.inflate(inflater, container, false)
    }

    override fun bind(){
        with(binding) {

            statusAdapter = StatusAdapter{ status ->
                viewModel.onStatusSelected(status)
            }
            rvStatuses.adapter = statusAdapter
            rvStatuses.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            orderAdapter = OrderAdapter { order ->
                findNavController().navigate(OrdersDashboardFragmentDirections.actionOrdersDashboardFragmentToDetailsFragment(order))
            }

            rvOrders.adapter = orderAdapter
            rvOrders.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.statuses.collectLatest { statuses ->
                            statusAdapter.submitList(statuses)
                        }
                    }
                    launch {
                        viewModel.filteredOrders.collectLatest { orders ->
                            orderAdapter.submitList(orders)
                        }
                    }
                }
            }
            setFragmentResultListener("orderUpdated") { _, bundle ->
                val updatedOrder = bundle.getSerializable("updatedOrder") as? Order
                updatedOrder?.let {
                    viewModel.updateOrder(it)
                }
            }


        }
    }



}