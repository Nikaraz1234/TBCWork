package com.example.tbcworks.screens.dashboard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.screens.dashboard.items.Order
import com.example.tbcworks.screens.dashboard.items.OrderStatus
import com.example.tbcworks.screens.dashboard.items.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private var id = 1
    private val _statuses = MutableStateFlow<List<Status>>(
        listOf(
            Status(OrderStatus.PENDING, isSelected = true),
            Status(OrderStatus.DELIVERED),
            Status(OrderStatus.CANCELED)
        )

    )
    val statuses = _statuses.asStateFlow()

    init {
        filterOrders(OrderStatus.PENDING)
    }
    private val _orders = MutableStateFlow(
        listOf(
            Order(
                id++,
                1524,
                "1234",
                5,
                System.currentTimeMillis(),
                100,
                OrderStatus.PENDING
            ),
            Order(
                id++,
                1525,
                "5678",
                3,
                System.currentTimeMillis(),
                50,
                OrderStatus.DELIVERED
            ),
            Order(
                id++,
                1526,
                "9101",
                2,
                System.currentTimeMillis(),
                20, OrderStatus.CANCELED
            )
        )
    )
    private val _filteredOrders = MutableStateFlow<List<Order>>(emptyList())
    val filteredOrders = _filteredOrders.asStateFlow()

    fun onStatusSelected(orderStatus: OrderStatus) {
        viewModelScope.launch {
            _statuses.update { list ->
                list.map { it.copy(isSelected = it.status == orderStatus) }
            }
            filterOrders(orderStatus)
        }
    }

    fun filterOrders(orderStatus: OrderStatus) {
        viewModelScope.launch {
            _filteredOrders.value = _orders.value.filter { it.status == orderStatus }
        }
    }
    fun updateOrder(updatedOrder: Order) {
        _orders.value = _orders.value.map { if (it.id == updatedOrder.id) updatedOrder else it }
        val selectedStatus = _statuses.value.firstOrNull { it.isSelected }?.status
        selectedStatus?.let { status ->
            _filteredOrders.value = _orders.value.filter { it.status == status }
        }
    }
}