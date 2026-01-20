package com.example.tbcworks.ui.screen.dashboard

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tbcworks.ui.screen.dashboard.model.CategoryModel
import com.example.tbcworks.ui.screen.dashboard.model.ProductModel
import com.example.tbcworks.ui.theme.AppBg
import com.example.tbcworks.ui.theme.SelectedCategory
import com.example.tbcworks.ui.theme.UnselectedCategory


@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(DashboardContract.Event.LoadCategories)
        viewModel.onEvent(DashboardContract.Event.LoadProducts)
        println("Products: ${state.products.size}")
        println("Categories: ${state.categories.size}")
    }

    DashboardContent(
        products = state.filteredProducts,
        categories = state.categories,
        onProductClick = { productId ->
            viewModel.onEvent(DashboardContract.Event.ProductClicked(productId))
        },
        selectedCategoryId = state.selectedCategoryId,
        onCategoryClick = { categoryId ->
            viewModel.onEvent(
                DashboardContract.Event.CategoryClicked(categoryId)
            )
        },
    )
}

@Composable
fun DashboardContent(
    products: List<ProductModel>,
    categories: List<CategoryModel>,
    selectedCategoryId: Int?,
    onCategoryClick: (Int) -> Unit,
    onProductClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBg)
            .padding(16.dp)
    ) {


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->

                val isSelected = selectedCategoryId == category.id

                val backgroundColor = if (isSelected) {
                    SelectedCategory
                } else {
                    UnselectedCategory
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(backgroundColor)
                        .clickable {
                            onCategoryClick(category.id)
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = category.category,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }



        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
                items(products){ product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onProductClick(product.id) },
                    colors = CardDefaults.cardColors(
                        containerColor = AppBg
                    )
                ) {
                    Column {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)) // rounded top corners
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(product.image),
                                contentDescription = product.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .matchParentSize()
                                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.BottomStart)
                                    .offset(x = 16.dp, y = -16.dp)
                                    .background(Color.Gray, shape = RoundedCornerShape(50))
                            )

                        }


                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = product.title,
                                color = Color.Gray,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "$${product.price}",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {

    val previewCategories = listOf(
        CategoryModel(id = 1, category = "All"),
        CategoryModel(id = 2, category = "Camping"),
        CategoryModel(id = 3, category = "Casual"),
        CategoryModel(id = 4, category = "Old Money")
    )

    val previewProducts = listOf(
        ProductModel(
            id = 1,
            title = "Leather Jacket",
            price = "129.99",
            category = "Casual",
            image = "https://via.placeholder.com/150"
        ),
        ProductModel(
            id = 2,
            title = "Camping Tent",
            price = "249.50",
            category = "Camping",
            image = "https://i.pinimg.com/736x/93/91/13/93911306e9a16d42ac0485cfef882bd3.jpg"
        ),
        ProductModel(
            id = 3,
            title = "Classic Watch",
            price = "399.00",
            category = "Old Money",
            image = "https://via.placeholder.com/150"
        )
    )

    DashboardContent(
        products = previewProducts,
        categories = previewCategories,
        onProductClick = {},
        selectedCategoryId = 1,
        onCategoryClick = {},
    )
}