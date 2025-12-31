package com.example.tbcworks.presentation.screen.search

sealed class SearchSideEffect {
    data class ShowMessage(val message: String) : SearchSideEffect()
}