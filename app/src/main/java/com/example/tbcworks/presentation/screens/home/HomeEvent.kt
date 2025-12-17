package com.example.tbcworks.presentation.screens.home

sealed class HomeEvent {
    data class AddMoneyToUser(val amount: Double) : HomeEvent()

}