package com.example.tbcworks.presentation.screens.map

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.GetLocationsUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.screens.map.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : BaseViewModel<MapState, MapSideEffect, MapEvent>(MapState()) {

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.LoadLocations -> loadLocations()
        }
    }

    private fun loadLocations() {
        viewModelScope.launch {
            getLocationsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> setState { copy(isLoading = true, error = null) }
                    is Resource.Success -> setState {
                        copy(isLoading = false, locations = resource.data.map { it.toPresentation() })
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false, error = resource.message) }
                        sendSideEffect(MapSideEffect.ShowError(resource.message))
                    }
                }
            }
        }
    }
}
