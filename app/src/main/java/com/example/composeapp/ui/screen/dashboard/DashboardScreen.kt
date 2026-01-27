package com.example.composeapp.ui.screen.dashboard

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composeapp.R
import com.example.composeapp.ui.components.Loader
import com.example.composeapp.ui.screen.dashboard.model.OrderModel
import com.example.composeapp.ui.screen.dashboard.model.OrderStatus
import com.example.composeapp.ui.theme.AppTypography
import com.example.composeapp.ui.theme.Black
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.theme.Radius
import com.example.composeapp.ui.theme.Spacer
import com.example.composeapp.ui.theme.Transparent
import com.example.composeapp.ui.theme.Typography
import com.example.composeapp.ui.theme.White
import com.example.composeapp.ui.theme.canceled
import com.example.composeapp.ui.theme.delivered
import com.example.composeapp.ui.theme.pending
import com.example.composeapp.ui.theme.selectedCategory
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        viewModel.onEvent(DashboardContract.Event.LoadOrders)
        viewModel.onEvent(DashboardContract.Event.GetTheme)
    }
    LaunchedEffect(state) {
        Log.d("DashboardScreen", "State: $state")
    }

    ComposeAppTheme(darkTheme = state.isDarkTheme){
        DashboardContent(
            state = state,
            onEvent = viewModel::onEvent,
            onOrderDetailsClick = { order ->
                if (order.status == OrderStatus.PENDING) {
                    viewModel.onEvent(DashboardContract.Event.SelectOrder(order))
                    coroutineScope.launch { bottomSheetState.show() }
                }
            }
        )

        state.selectedOrder?.let { selectedOrder ->
            ModalBottomSheet(
                onDismissRequest = {
                    coroutineScope.launch { bottomSheetState.hide() }
                    viewModel.onEvent(DashboardContract.Event.SelectOrder(null))
                },
                sheetState = bottomSheetState
            ) {
                StatusBottomSheetContent(
                    order = selectedOrder,
                    onStatusChange = { newStatus ->
                        viewModel.onEvent(DashboardContract.Event.UpdateOrderStatus(selectedOrder.id, newStatus))
                        coroutineScope.launch { bottomSheetState.hide() }
                        viewModel.onEvent(DashboardContract.Event.SelectOrder(null))
                    }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    state: DashboardContract.State,
    onEvent: (DashboardContract.Event) -> Unit,
    onOrderDetailsClick: (OrderModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = Spacer.spacer18)
        ) {
            IconButton(
                onClick = { onEvent(DashboardContract.Event.ToggleTheme) },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_theme),
                    contentDescription = stringResource(R.string.theme),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )
            }

            Text(
                text = stringResource(R.string.title),
                modifier = Modifier.align(Alignment.Center),
                style = AppTypography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            IconButton(
                onClick = { },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = stringResource(R.string.notifications),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        val categories = OrderStatus.entries.toTypedArray()
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = Spacer.spacer18, vertical = Spacer.spacer8),
            horizontalArrangement = Arrangement.spacedBy(Spacer.spacer8)
        ) {
            items(categories, key = { it }) { category ->
                CategoryItem(
                    text = category.name,
                    isSelected = category == state.selectedCategory,
                    onClick = { onEvent(DashboardContract.Event.SelectCategory(category)) }
                )
            }
        }

        if (state.isLoading) {
            Loader()
        }

        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = { onEvent(DashboardContract.Event.Refresh) },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                contentPadding = PaddingValues(vertical = Spacer.spacer18, horizontal = Spacer.spacer16),
                verticalArrangement = Arrangement.spacedBy(Spacer.spacer16)
            ) {
                items(state.filteredOrders, key = { it.id }) { order ->
                    OrderItem(
                        order = order,
                        onDetailsClick = { if (order.status == OrderStatus.PENDING) onOrderDetailsClick(order) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = Spacer.spacer16, vertical = Spacer.spacer8)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = Typography.bodyMedium, color = textColor)
    }
}

@Composable
private fun OrderItem(
    order: OrderModel,
    onDetailsClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        shape = Radius.radius16,
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(Spacer.spacer16)) {
            val textColor = MaterialTheme.colorScheme.onSurface
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(R.string.order_number, order.orderNumber),
                    style = AppTypography.bodyLarge,
                    color = textColor
                )
                Text(text = order.date, style = AppTypography.bodySmall, color = textColor)
            }

            Spacer(modifier = Modifier.height(Spacer.spacer8))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.tracking_number), style = AppTypography.bodyMedium, color = textColor)
                Text(text = order.trackingNumber, style = AppTypography.bodyMedium, color = textColor)
            }

            Spacer(modifier = Modifier.height(Spacer.spacer8))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row {
                    Text(text = stringResource(R.string.quantity), style = AppTypography.bodyMedium, color = textColor)
                    Text(text = order.quantity.toString(), style = AppTypography.bodyMedium, color = textColor)
                }
                Row {
                    Text(text = stringResource(R.string.subtotal), style = AppTypography.bodyMedium, color = textColor)
                    Text(text = "$${order.subtotal}", style = AppTypography.bodyMedium, color = textColor)
                }
            }

            Spacer(modifier = Modifier.height(Spacer.spacer8))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {

                val statusColor = when(order.status) {
                    OrderStatus.PENDING -> pending
                    OrderStatus.DELIVERED -> delivered
                    OrderStatus.CANCELED -> canceled
                }

                Text(text = order.status.name, color = statusColor, style = AppTypography.bodyMedium)

                OutlinedButton(onClick = onDetailsClick) {
                    Text(text = stringResource(R.string.details), color = textColor)
                }
            }
        }
    }
}

@Composable
fun StatusBottomSheetContent(
    order: OrderModel,
    onStatusChange: (OrderStatus) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(Spacer.spacer16)) {

        Text(text = stringResource(R.string.order_number, order.orderNumber),
            style = Typography.titleMedium)

        Spacer(modifier = Modifier.height(Spacer.spacer8))

        Text(text = stringResource(R.string.current_status, order.status.name),
            style = Typography.bodyMedium)

        Spacer(modifier = Modifier.height(Spacer.spacer16))

        Text(text = stringResource(R.string.change_status_to),
            style = Typography.bodyMedium)

        Spacer(modifier = Modifier.height(Spacer.spacer8))

        Row(horizontalArrangement = Arrangement.spacedBy(Spacer.spacer8)) {

            OutlinedButton(onClick = { onStatusChange(OrderStatus.DELIVERED) }) {
                Text(text = stringResource(R.string.delivered))
            }

            OutlinedButton(onClick = { onStatusChange(OrderStatus.CANCELED) }) {
                Text(text = stringResource(R.string.canceled))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    val fakeState = DashboardContract.State(
        isLoading = false,
        orders = listOf(
            OrderModel(
                id = 1,
                orderNumber = "1524",
                date = "13/05/2025",
                trackingNumber = "IK287368838",
                quantity = 2,
                subtotal = 110,
                status = OrderStatus.PENDING
            ),
            OrderModel(
                id = 2,
                orderNumber = "1525",
                date = "14/05/2025",
                trackingNumber = "IK287368839",
                quantity = 1,
                subtotal = 50,
                status = OrderStatus.DELIVERED
            )
        ),
        filteredOrders = listOf(

            OrderModel(
                id = 1,
                orderNumber = "1524",
                date = "13/05/2025",
                trackingNumber = "IK287368838",
                quantity = 2,
                subtotal = 110,
                status = OrderStatus.PENDING
            )
        ),
        selectedCategory = OrderStatus.PENDING
    )

    DashboardContent(
        state = fakeState,
        onEvent = {},
        onOrderDetailsClick = {}
    )
}