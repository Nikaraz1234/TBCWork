package com.example.tbcworks.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.common.dataStore.TokenDataStore
import com.example.tbcworks.domain.usecase.datastore_pref.ClearUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val clearUserSessionUseCase: ClearUserSessionUseCase
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LogoutClicked -> logout()
            HomeEvent.UserListClicked -> navigateToDashboard()
            HomeEvent.UserInfoClicked -> navigateToUserInfo()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            clearUserSessionUseCase()
            _sideEffect.emit(HomeSideEffect.NavigateToLogin)
        }
    }

    private fun navigateToDashboard() {
        viewModelScope.launch {
            _sideEffect.emit(HomeSideEffect.NavigateToDashboard)
        }
    }
    private fun navigateToUserInfo(){
        viewModelScope.launch {
            _sideEffect.emit(HomeSideEffect.NavigateToUserInfo)
        }
    }
}