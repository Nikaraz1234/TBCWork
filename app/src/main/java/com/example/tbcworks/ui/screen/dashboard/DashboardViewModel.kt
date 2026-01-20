package com.example.tbcworks.ui.screen.dashboard

import com.example.tbcworks.domain.usecase.category.GetCategoriesUseCase
import com.example.tbcworks.domain.usecase.product.GetProductsUseCase
import com.example.tbcworks.ui.common.BaseViewModel
import com.example.tbcworks.ui.screen.dashboard.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : BaseViewModel<DashboardContract.State, DashboardContract.SideEffect, DashboardContract.Event>(
    initialState = DashboardContract.State()
) {

    fun onEvent(event: DashboardContract.Event) {
        when (event) {
            is DashboardContract.Event.LoadProducts -> fetchProducts()
            is DashboardContract.Event.LoadCategories -> fetchCategories()
            is DashboardContract.Event.ProductClicked -> {
                sendSideEffect(DashboardContract.SideEffect.NavigateToProductDetails)
            }

            is DashboardContract.Event.CategoryClicked -> {
                val selectedId = event.categoryId
                val currentState = uiState.value

                val selectedCategoryName = currentState.categories
                    .firstOrNull { it.id == selectedId }?.category ?: "All"

                val filtered = if (selectedCategoryName.lowercase() == "all") {
                    currentState.products
                } else {
                    currentState.products.filter { it.category == selectedCategoryName }
                }

                setState {
                    copy(
                        selectedCategoryId = selectedId,
                        filteredProducts = filtered
                    )
                }
            }

        }
    }

    private fun fetchProducts() {
        handleResponse(
            apiCall = { getProductsUseCase() },
            onSuccess = { products ->
                setState { copy(products = products.map { it.toPresentation() }, isLoadingProducts = false) }
            },
            onError = { message ->
                setState { copy(isLoadingProducts = false, errorMessage = message) }
                sendSideEffect(DashboardContract.SideEffect.ShowToast(message))
            },
            onLoading = {
                setState { copy(isLoadingProducts = true) }
            }
        )
    }

    private fun fetchCategories() {
        handleResponse(
            apiCall = { getCategoriesUseCase() },
            onSuccess = { categories ->
                setState { copy(categories = categories.map { it.toPresentation() }, isLoadingCategories = false) }
            },
            onError = { message ->
                setState { copy(isLoadingCategories = false, errorMessage = message) }
                sendSideEffect(DashboardContract.SideEffect.ShowToast(message))
            },
            onLoading = {
                setState { copy(isLoadingCategories = true) }
            }
        )
    }
}
