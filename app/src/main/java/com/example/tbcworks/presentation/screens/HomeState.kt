package com.example.tbcworks.presentation.screens


import com.example.tbcworks.presentation.screens.model.PostModel
import com.example.tbcworks.presentation.screens.model.StoryModel

data class HomeState(
    val posts : List<PostModel> = emptyList(),
    val stories: List<StoryModel> = emptyList(),
    val error: String? = null,
    val isLoading : Boolean = false,
    val isNetworkAvailable: Boolean = true
)
