package com.example.tbcworks.presentation.screens.home

sealed class HomeEvent {
    object LoadPosts : HomeEvent()
    object LoadStories : HomeEvent()
}