package com.example.tbcworks.presentation.screens.home


data class HomeState(
    val locations: List<LocationModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)