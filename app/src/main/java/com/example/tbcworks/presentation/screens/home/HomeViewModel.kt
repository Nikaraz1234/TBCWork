package com.example.tbcworks.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.firebase.GetCurrentUserIdUseCase
import com.example.tbcworks.domain.usecase.user.AddMoneyToUserUseCase
import com.example.tbcworks.domain.usecase.user.GetUserBalanceUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserBalanceUseCase: GetUserBalanceUseCase,
    private val addMoneyToUserUseCase: AddMoneyToUserUseCase
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(initialState = HomeState()) {

    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    init {
        loadUserBalance()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Logout -> logout()
            is HomeEvent.AddMoneyToUser -> addMoneyToUser(event.amount)
            HomeEvent.LoadBalance -> loadUserBalance()
        }
    }

    private fun loadUserBalance() {
        val userId = getCurrentUserIdUseCase() ?: return
        viewModelScope.launch {
            getUserBalanceUseCase(userId).collect { resource ->
                if(resource is Resource.Success) {
                    _balance.value = resource.data
                }
            }
        }
    }
    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        sendSideEffect(HomeSideEffect.NavigateToLogin)
    }

    private fun addMoneyToUser(amount: Double) {
        if (amount <= 0.0) {
            sendSideEffect(HomeSideEffect.ShowSnackBar("Enter a valid amount"))
            return
        }

        val userId = getCurrentUserIdUseCase()
        if (userId == null) {
            sendSideEffect(HomeSideEffect.ShowSnackBar("User not logged in"))
            return
        }

        viewModelScope.launch {
            addMoneyToUserUseCase(userId, amount).collect { resource ->
                when (resource) {

                    is Resource.Success -> {
                        loadUserBalance()
                        sendSideEffect(HomeSideEffect.ShowSnackBar("Money added successfully"))
                    }
                    is Resource.Error -> {
                        sendSideEffect(HomeSideEffect.ShowSnackBar(resource.message ?: "Failed to add money"))
                    }
                    Resource.Loading -> {
                        // Optional: handle loading state
                    }
                }
            }
        }
    }
}

