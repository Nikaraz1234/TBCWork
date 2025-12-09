package com.example.tbcworks.presentation.screens

import com.example.tbcworks.domain.usecase.GetPostsUseCase
import com.example.tbcworks.domain.usecase.GetStoriesUseCase
import com.example.tbcworks.domain.usecase.NetworkCheckUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HomeViewModel(
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

    private fun loadPosts(){

    }
    private fun loadStories(){

    }
    private fun checkNetwork(): Boolean {
        return networkCheckUseCase()
    }
}