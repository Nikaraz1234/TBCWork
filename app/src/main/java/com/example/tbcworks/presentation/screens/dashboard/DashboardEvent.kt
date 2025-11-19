package com.example.tbcworks.presentation.screens.dashboard

sealed class DashboardEvent {
    data object FetchUsers : DashboardEvent()
}