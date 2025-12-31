package com.example.tbcworks.presentation.screen.search

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.GetFilteredCategoriesUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.screen.search.mapper.toPresentation
import com.example.tbcworks.presentation.screen.search.model.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getFilteredCategories: GetFilteredCategoriesUseCase
) : BaseViewModel<SearchState, SearchSideEffect, SearchEvent>(SearchState()) {

    init {
        uiState
            .map { it.query }
            .distinctUntilChanged()
            .debounce(500)
            .onEach { query ->
                handleResponse(
                    apiCall = { getFilteredCategories(query) },

                    onLoading = {
                        setState { copy(isLoading = true) }
                    },

                    onSuccess = { data ->
                        val flat = data
                            .toPresentation()
                            .map { it.copy(isExpanded = false) }

                        setState {
                            copy(
                                isLoading = false,
                                allCategories = flat,
                                visibleCategories = flat.filter { it.depth == 0 }
                            )
                        }
                    },

                    onError = { message ->
                        setState { copy(isLoading = false) }
                        sendSideEffect(
                            SearchSideEffect.ShowMessage(message)
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChange ->
                setState { copy(query = event.query) }

            is SearchEvent.OnCategoryClick ->
                toggleCategory(event.category)
        }
    }

    private fun toggleCategory(category: CategoryModel) {
        val updated = uiState.value.allCategories.map {
            if (it.id == category.id)
                it.copy(isExpanded = !it.isExpanded)
            else it
        }

        setState {
            copy(
                allCategories = updated,
                visibleCategories = computeVisible(updated)
            )
        }
    }

    private fun computeVisible(
        all: List<CategoryModel>
    ): List<CategoryModel> {
        val expandedIds = all
            .filter { it.isExpanded }
            .map { it.id }
            .toSet()

        return all.filter { item ->
            if (item.depth == 0) return@filter true

            generateSequence(item.parentId) { parentId ->
                all.firstOrNull { it.id == parentId }?.parentId
            }.all { it in expandedIds }
        }
    }
}
