package com.example.tbcworks.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.firebase.GetCurrentUserIdUseCase
import com.example.tbcworks.domain.usecase.user.AddMoneyToUserUseCase
import com.example.tbcworks.domain.usecase.user.GetUserBalanceUseCase
import com.example.tbcworks.domain.usecase.user.WithdrawMoneyFromUserUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val addMoneyToUserUseCase: AddMoneyToUserUseCase,
    private val withdrawMoneyFromUserUseCase: WithdrawMoneyFromUserUseCase,
    private val getUserBalanceUseCase: GetUserBalanceUseCase
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(initialState = HomeState()) {

    init {
        observeBalance()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Logout -> logout()
            is HomeEvent.AddMoneyToUser -> addMoney(event.amount)
        }
    }

    private fun observeBalance() {
        val userId = getCurrentUserIdUseCase() ?: return
        viewModelScope.launch {
            getUserBalanceUseCase(userId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> setState { copy(isLoading = true) }
                    is Resource.Success -> setState { copy(balance = resource.data, isLoading = false) }
                    is Resource.Error -> {
                        setState { copy(isLoading = false) }
                        sendSideEffect(HomeSideEffect.ShowSnackBar(resource.message ?: "Failed to load balance"))
                    }
                }
            }
        }
    }

    private fun addMoney(amount: Double) {
        if (amount <= 0.0) {
            sendSideEffect(HomeSideEffect.ShowSnackBar("Enter a valid amount"))
            return
        }

        val userId = getCurrentUserIdUseCase() ?: run {
            sendSideEffect(HomeSideEffect.ShowSnackBar("User not logged in"))
            return
        }

        viewModelScope.launch {
            addMoneyToUserUseCase(userId, amount).collect { resource ->
                when (resource) {
                    is Resource.Loading -> setState { copy(isLoading = true) }
                    is Resource.Success -> sendSideEffect(HomeSideEffect.ShowSnackBar("Money added successfully"))
                    is Resource.Error -> sendSideEffect(HomeSideEffect.ShowSnackBar(resource.message ?: "Failed to add money"))
                }
            }
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        sendSideEffect(HomeSideEffect.NavigateToLogin)
    }
}
