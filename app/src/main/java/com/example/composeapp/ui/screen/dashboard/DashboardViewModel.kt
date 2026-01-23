package com.example.composeapp.ui.screen.dashboard

import com.example.composeapp.domain.usecase.GetOrdersUseCase
import com.example.composeapp.ui.common.BaseViewModel
import com.example.composeapp.ui.screen.dashboard.mapper.toPresentation
import com.example.composeapp.ui.screen.dashboard.model.OrderModel
import com.example.composeapp.ui.screen.dashboard.model.OrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : BaseViewModel<DashboardContract.State, DashboardContract.SideEffect, DashboardContract.Event>(
    initialState = DashboardContract.State()
) {

    fun onEvent(event: DashboardContract.Event) {
        when (event) {
            is DashboardContract.Event.LoadOrders -> loadOrders()
            is DashboardContract.Event.SelectCategory -> selectCategory(event.category)
            DashboardContract.Event.Refresh -> refresh()
            is DashboardContract.Event.SelectOrder -> {
                setState { copy(selectedOrder = event.order) }
            }
            is DashboardContract.Event.UpdateOrderStatus -> {
                updateOrderStatus(event.orderId, event.status)
            }
        }
    }

    private fun updateOrderStatus(orderId: Int, newStatus: OrderStatus) {
        setState {
            val updatedOrders = orders.map {
                if (it.id == orderId) it.copy(status = newStatus) else it
            }

            val updatedFilteredOrders = updatedOrders.filter { it.status == selectedCategory }

            copy(
                orders = updatedOrders,
                filteredOrders = updatedFilteredOrders,
                selectedOrder = updatedOrders.firstOrNull { it.id == orderId }
            )
        }
    }


    private fun refresh() {
        setState { copy(isLoading = true) }

        loadOrders()
        setState {
            copy(
                selectedCategory = OrderStatus.PENDING,
                filteredOrders = filterOrders(uiState.value.orders, OrderStatus.PENDING),
                isLoading = false
            )
        }
    }

    private fun selectCategory(category: OrderStatus) {
        setState { copy(
            selectedCategory = category,
            filteredOrders = uiState.value.orders.filter { it.status == category }
            )
        }
    }

    private fun loadOrders() {
        handleResponse(
            apiCall = { getOrdersUseCase() },
            onSuccess = { orders ->
                setState {
                    val presentationOrders = orders.map { it.toPresentation() }
                    copy(
                        orders = presentationOrders,
                        filteredOrders = filterOrders(presentationOrders, uiState.value.selectedCategory),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            },
            onError = { message ->
                setState { copy(isLoading = false, errorMessage = message) }
                sendSideEffect(DashboardContract.SideEffect.ShowError(message))
            },
            onLoading = {
                setState { copy(isLoading = true) }
            }
        )
    }
    private fun filterOrders(orders: List<OrderModel>, category: OrderStatus): List<OrderModel> {
        return orders.filter { it.status == category }
    }

}
