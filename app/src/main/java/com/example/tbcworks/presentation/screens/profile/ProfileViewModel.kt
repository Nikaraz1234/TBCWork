package com.example.tbcworks.presentation.screens.profile

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.firebase.GetCurrentUserEmailUseCase
import com.example.tbcworks.domain.usecase.firebase.GetCurrentUserIdUseCase
import com.example.tbcworks.domain.usecase.login.LogoutUseCase
import com.example.tbcworks.domain.usecase.pots.GetUserPotsCountUseCase
import com.example.tbcworks.domain.usecase.transaction.GetTransactionsUseCase
import com.example.tbcworks.domain.usecase.user.ChangePasswordUseCase
import com.example.tbcworks.domain.usecase.user.DeleteAccountUseCase
import com.example.tbcworks.domain.usecase.user.GetUserBalanceUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getCurrentUserEmailUseCase: GetCurrentUserEmailUseCase,
    private val getUserBalanceUseCase: GetUserBalanceUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getUserPotsCountUseCase: GetUserPotsCountUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : BaseViewModel<ProfileState, ProfileSideEffect, ProfileEvent>(
    ProfileState()
) {

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LoadProfileData -> loadProfileData()
            ProfileEvent.Logout -> logout()
            ProfileEvent.DeleteAccount -> deleteAccount()
            is ProfileEvent.ChangePassword -> changePassword(event.current, event.newPassword)
        }
    }

    private fun loadProfileData() {
        val userId = getCurrentUserIdUseCase() ?: return
        val email = getCurrentUserEmailUseCase().orEmpty()

        setState { copy(isLoading = true, email = email) }

        observeBalance(userId)
        observeTransactions(userId)
        observePotsCount(userId)
    }

    private fun observeBalance(userId: String) {
        viewModelScope.launch {
            getUserBalanceUseCase(userId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        setState { copy(balance = resource.data) }
                    }
                    is Resource.Error -> {
                        setState { copy(error = resource.message) }
                    }
                    Resource.Loading -> Unit
                }
            }
        }
    }

    private fun observeTransactions(userId: String) {
        viewModelScope.launch {
            getTransactionsUseCase(userId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        setState { copy(transactionsCount = resource.data.size) }
                    }
                    is Resource.Error -> {
                        setState { copy(error = resource.message) }
                    }
                    Resource.Loading -> Unit
                }
            }
        }
    }

    private fun observePotsCount(userId: String) {
        viewModelScope.launch {
            getUserPotsCountUseCase(userId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        setState {
                            copy(
                                potsCount = resource.data,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        setState {
                            copy(
                                error = resource.message,
                                isLoading = false
                            )
                        }
                    }
                    Resource.Loading -> Unit
                }
            }
        }
    }

    private fun logout() {
        logoutUseCase()
        sendSideEffect(ProfileSideEffect.NavigateToLogin)
    }

    private fun deleteAccount() {
        viewModelScope.launch {
            deleteAccountUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> sendSideEffect(ProfileSideEffect.NavigateToLogin)
                    is Resource.Error -> sendSideEffect(ProfileSideEffect.ShowSnackBar(result.message ?: "Error deleting account"))
                    Resource.Loading -> setState { copy(isLoading = true) }
                }
            }
        }
    }
    fun changePassword(current: String, newPassword: String) {
        viewModelScope.launch {
            changePasswordUseCase(current, newPassword).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        setState { copy(isLoading = false) }
                        sendSideEffect(
                            ProfileSideEffect.ShowSnackBar("Password changed successfully")
                        )
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false) }
                        sendSideEffect(
                            ProfileSideEffect.ShowSnackBar(resource.message ?: "Failed to change password")
                        )
                    }
                    Resource.Loading -> setState { copy(isLoading = true) }
                }
            }
        }
    }

}
