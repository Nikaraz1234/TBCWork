package com.example.composeapp.ui.screen.dashboard

import com.example.composeapp.ui.screen.dashboard.model.OrderModel
import com.example.composeapp.ui.screen.dashboard.model.OrderStatus

object DashboardContract {
    data class State(
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val orders: List<OrderModel> = emptyList(),
        val filteredOrders: List<OrderModel> = emptyList(),
        val selectedCategory: OrderStatus = OrderStatus.PENDING,
        val errorMessage: String? = null,
        val selectedOrder: OrderModel? = null,
        val isDarkTheme: Boolean = false
    )
    sealed class SideEffect {
        data class ShowError(val message: String) : SideEffect()
    }

    sealed class Event {
        object LoadOrders : Event()
        data class SelectCategory(val category: OrderStatus) : Event()
        object Refresh : Event()
        data class SelectOrder(val order: OrderModel?) : Event()
        data class UpdateOrderStatus(val orderId: Int, val status: OrderStatus) : Event()
        object ToggleTheme : Event()
        object GetTheme: Event()


    }

}