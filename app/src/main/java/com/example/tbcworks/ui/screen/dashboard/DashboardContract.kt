package com.example.tbcworks.ui.screen.dashboard

import com.example.tbcworks.ui.screen.dashboard.model.CategoryModel
import com.example.tbcworks.ui.screen.dashboard.model.ProductModel

object DashboardContract {
    data class State(
        val products: List<ProductModel> = emptyList(),
        val filteredProducts: List<ProductModel> = emptyList(),
        val categories: List<CategoryModel> = emptyList(),
        val isLoadingProducts: Boolean = false,
        val isLoadingCategories: Boolean = false,
        val errorMessage: String? = null,
        val selectedCategoryId: Int? = null,
    )

    sealed class SideEffect {
        data class ShowToast(val message: String) : SideEffect()
        object NavigateToProductDetails : SideEffect()
    }

    sealed class Event {
        object LoadProducts : Event()
        object LoadCategories : Event()
        data class ProductClicked(val productId: Int) : Event()
        data class CategoryClicked(val categoryId: Int) : Event()
    }
}