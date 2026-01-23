package com.example.composeapp.ui.screen.dashboard

import com.example.composeapp.domain.Resource
import com.example.composeapp.domain.model.Order
import com.example.composeapp.domain.repository.OrderRepository
import com.example.composeapp.domain.usecase.GetOrdersUseCase
import com.example.composeapp.ui.screen.dashboard.model.OrderStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private lateinit var fakeRepository: OrderRepository
    private lateinit var viewModel: DashboardViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val sampleOrders = listOf(
        Order(1, "1524", "13/05/2025", "IK287368838", 2, 110, "PENDING"),
        Order(2, "1525", "14/05/2025", "IK287368839", 1, 50, "CANCELED")
    )

    class FakeOrderRepository(private val orders: List<Order>) : OrderRepository {
        override fun getOrders(): Flow<Resource<List<Order>>> = flow {
            emit(Resource.Success(orders))
        }
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeRepository = FakeOrderRepository(sampleOrders)
        val useCase = GetOrdersUseCase(fakeRepository)
        viewModel = DashboardViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadOrders success sets state correctly`() = testScope.runTest {
        viewModel.onEvent(DashboardContract.Event.LoadOrders)

        testScheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(2, state.orders.size)
        assertTrue(state.filteredOrders.all { it.status == OrderStatus.PENDING })
        assertTrue(!state.isLoading)
        assertEquals(null, state.errorMessage)
    }

    @Test
    fun `updateOrderStatus changes order`() = testScope.runTest {
        viewModel.onEvent(DashboardContract.Event.LoadOrders)
        testScheduler.advanceUntilIdle()

        viewModel.onEvent(DashboardContract.Event.UpdateOrderStatus(1, OrderStatus.DELIVERED))
        testScheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        val updatedOrder = state.orders.first { it.id == 1 }
        assertEquals(OrderStatus.DELIVERED, updatedOrder.status)

        assertTrue(state.filteredOrders.all { it.status == OrderStatus.PENDING })

        assertEquals(OrderStatus.DELIVERED, state.selectedOrder?.status)
    }

    @Test
    fun `selectCategory filters orders correctly`() = testScope.runTest {
        viewModel.onEvent(DashboardContract.Event.LoadOrders)
        testScheduler.advanceUntilIdle()

        viewModel.onEvent(DashboardContract.Event.SelectCategory(OrderStatus.DELIVERED))
        testScheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(OrderStatus.DELIVERED, state.selectedCategory)
        assertTrue(state.filteredOrders.all { it.status == OrderStatus.DELIVERED })
    }

    @Test
    fun `refresh resets category and filteredOrders`() = testScope.runTest {
        viewModel.onEvent(DashboardContract.Event.LoadOrders)
        testScheduler.advanceUntilIdle()

        viewModel.onEvent(DashboardContract.Event.SelectCategory(OrderStatus.DELIVERED))
        testScheduler.advanceUntilIdle()

        viewModel.onEvent(DashboardContract.Event.Refresh)
        testScheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(OrderStatus.PENDING, state.selectedCategory)
        assertTrue(state.filteredOrders.all { it.status == OrderStatus.PENDING })
        assertTrue(!state.isLoading)
    }
}
