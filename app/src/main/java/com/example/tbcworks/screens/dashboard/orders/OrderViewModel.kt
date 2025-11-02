package com.example.tbcworks.screens.dashboard.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class OrderViewModel: ViewModel() {
    private  var id = 1
    private val _orders = MutableStateFlow<List<Order>>(
        listOf(
            Order(
                id = id++,
                name = "Red Chair",
                colorName = "Red",
                color = R.color.red_tshirt,
                image = R.drawable.icon_chair,
                quantity = 2,
                price = 19.99,
                status = OrderStatus.ACTIVE,
                reviews = mutableListOf()
            ),
            Order(
                id = id++,
                name = "Blue Chair",
                colorName = "Blue",
                color = R.color.blue_jeans,
                image = R.drawable.icon_chair,
                quantity = 1,
                price = 49.99,
                status = OrderStatus.COMPLETED,
                reviews = mutableListOf()
            ),
            Order(
                id = id++,
                name = "Black Chair",
                colorName = "Black",
                color = R.color.black_sneakers,
                image = R.drawable.icon_chair,
                quantity = 1,
                price = 79.99,
                status = OrderStatus.ACTIVE,
                reviews = mutableListOf()
            ),
            Order(
                id = id++,
                name = "White Chair",
                colorName = "White",
                color = R.color.white_cap,
                image = R.drawable.icon_chair,
                quantity = 3,
                price = 15.99,
                status = OrderStatus.COMPLETED,
                reviews = mutableListOf()
            )
        )
    )

    private val _filteredOrders = MutableStateFlow<List<Order>>(
        emptyList()
    )
    val filteredOrders = _filteredOrders.asStateFlow()

    init {
        filterOrders(OrderStatus.ACTIVE)
    }

    fun filterOrders(orderStatus: OrderStatus){
        viewModelScope.launch {
            _filteredOrders.value = _orders.value.filter { it.status == orderStatus}
        }
    }
    fun addReview(review : String, orderId: Int){
        viewModelScope.launch {
            _orders.value = _orders.value.map { order ->
                if (order.id == orderId) {
                    order.copy(reviews = (order.reviews + review) as MutableList<String>)
                } else order
            }

            _filteredOrders.value = _orders.value.filter { it.status == OrderStatus.COMPLETED }
        }


    }

}