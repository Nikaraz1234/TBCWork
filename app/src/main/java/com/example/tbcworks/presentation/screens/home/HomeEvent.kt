package com.example.tbcworks.presentation.screens.home

sealed class HomeEvent {
    object Logout : HomeEvent()
    data class AddMoneyToUser(val amount: Double) : HomeEvent()
    object LoadBalance : HomeEvent()

}