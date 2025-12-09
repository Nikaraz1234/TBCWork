package com.example.tbcworks.presentation.screens

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.GetPostsUseCase
import com.example.tbcworks.domain.usecase.GetStoriesUseCase
import com.example.tbcworks.domain.usecase.NetworkCheckUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.screens.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkCheckUseCase: NetworkCheckUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val getStoriesUseCase: GetStoriesUseCase
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(
    initialState = HomeState()
) {
    fun onEvent(event: HomeEvent){
        when (event) {
            is HomeEvent.LoadPosts -> loadPosts()
            is HomeEvent.LoadStories -> loadStories()
        }
    }
    private fun loadPosts() {
        viewModelScope.launch {
            val isConnected = networkCheckUseCase()
            setState { copy(isNetworkAvailable = isConnected) }

            getPostsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> setState {
                        copy(
                            posts = resource.data.map { it.toPresentation() },
                            isLoading = false
                        )
                    }
                    is Resource.Error -> sendSideEffect(HomeSideEffect.ShowMessage(resource.message))
                    is Resource.Loading -> setState { copy(isLoading = true) }
                }
            }
        }
    }
    private fun loadStories() {
        viewModelScope.launch {
            val isConnected = networkCheckUseCase()
            setState { copy(isNetworkAvailable = isConnected) }

            getStoriesUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> setState { copy(stories = resource.data.map { it.toPresentation() }, isLoading = false) }
                    is Resource.Error -> sendSideEffect(HomeSideEffect.ShowMessage(resource.message))
                    is Resource.Loading -> setState { copy(isLoading = true) }
                }
            }
        }
    }

}