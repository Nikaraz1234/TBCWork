package com.example.tbcworks.presentation.screens

sealed class HomeEvent {
    object LoadPosts : HomeEvent()
    object LoadStories : HomeEvent()
}