package com.example.tbcworks.presentation.screens.home


import com.example.tbcworks.presentation.screens.home.model.PostModel
import com.example.tbcworks.presentation.screens.home.model.StoryModel

data class HomeState(
    val posts : List<PostModel> = emptyList(),
    val stories: List<StoryModel> = emptyList(),
    val error: String? = null,
    val isLoading : Boolean = false,
    val isNetworkAvailable: Boolean = true
)
