package com.example.mycomposeapp.ui.screen.dashboard

import androidx.lifecycle.viewModelScope
import com.example.mycomposeapp.domain.keys.PreferenceKeys
import com.example.mycomposeapp.domain.usecase.datastore.GetPreferenceUseCase
import com.example.mycomposeapp.domain.usecase.datastore.SetPreferenceUseCase
import com.example.mycomposeapp.domain.usecase.location.GetLocationsUseCase
import com.example.mycomposeapp.ui.common.BaseViewModel
import com.example.mycomposeapp.ui.screen.dashboard.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getPreferenceUseCase: GetPreferenceUseCase,
    private val setPreferenceUseCase: SetPreferenceUseCase
) : BaseViewModel<DashboardContract.State, DashboardContract.SideEffect, DashboardContract.Event>(
    initialState = DashboardContract.State()
) {

    fun onEvent(event: DashboardContract.Event) {
        when (event) {
            DashboardContract.Event.LoadLocations -> loadLocations()
            DashboardContract.Event.Refresh -> loadLocations()
            DashboardContract.Event.GetTheme -> getTheme()
            DashboardContract.Event.ToggleTheme -> toggleTheme()
        }
    }

    private fun getTheme(){
        viewModelScope.launch {
            val theme = getPreferenceUseCase(PreferenceKeys.DARK_MODE, false).first()
            setState { copy(isDarkTheme = theme) }
        }
    }
    private fun toggleTheme() {
        viewModelScope.launch {
            val current = uiState.value.isDarkTheme
            setPreferenceUseCase(PreferenceKeys.DARK_MODE, !current)
            setState { copy(isDarkTheme = !current) }
        }
    }

    private fun loadLocations() {
        handleResponse(
            apiCall = { getLocationsUseCase() },
            onSuccess = { locations ->
                setState {
                    copy(
                        isLoading = false,
                        locations = locations.map { it.toPresentation() },
                        errorMessage = null
                    )
                }
            },
            onError = { message ->
                setState { copy(isLoading = false, errorMessage = message) }
                sendSideEffect(DashboardContract.SideEffect.ShowSnackBar(message))
            },
            onLoading = {
                setState { copy(isLoading = true, errorMessage = null) }
            }
        )
    }
}