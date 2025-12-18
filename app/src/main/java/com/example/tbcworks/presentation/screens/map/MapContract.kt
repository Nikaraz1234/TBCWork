package com.example.tbcworks.presentation.screens.map

import com.example.tbcworks.presentation.screens.map.model.LocationModel

sealed class MapEvent {
    object LoadLocations : MapEvent()
}

data class MapState(
    val isLoading: Boolean = false,
    val locations: List<LocationModel> = emptyList(),
    val error: String? = null
)

sealed class MapSideEffect {
    data class ShowError(val message: String) : MapSideEffect()
}