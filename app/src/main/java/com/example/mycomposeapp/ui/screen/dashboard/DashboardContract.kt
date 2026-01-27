package com.example.mycomposeapp.ui.screen.dashboard

import com.example.mycomposeapp.ui.screen.dashboard.model.LocationModel

object DashboardContract {

    data class State(
        val isLoading: Boolean = false,
        val locations: List<LocationModel> = emptyList(),
        val errorMessage: String? = null,
        val isDarkTheme: Boolean = false
    )

    sealed interface Event {
        data object LoadLocations : Event
        data object Refresh : Event
        object ToggleTheme : Event
        object GetTheme: Event
    }

    sealed interface SideEffect {
        data class ShowSnackBar(val message: String) : SideEffect

    }
}
