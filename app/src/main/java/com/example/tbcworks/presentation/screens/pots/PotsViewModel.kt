package com.example.tbcworks.presentation.screens.pots

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Pot
import com.example.tbcworks.domain.usecase.firebase.GetCurrentUserIdUseCase
import com.example.tbcworks.domain.usecase.pots.AddMoneyToPotUseCase
import com.example.tbcworks.domain.usecase.pots.AddPotUseCase
import com.example.tbcworks.domain.usecase.pots.DeletePotUseCase
import com.example.tbcworks.domain.usecase.pots.EditPotUseCase
import com.example.tbcworks.domain.usecase.pots.GetPotsUseCase
import com.example.tbcworks.domain.usecase.pots.WithdrawMoneyFromPotUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.screens.pots.mapper.toDomain
import com.example.tbcworks.presentation.screens.pots.mapper.toPresentation
import com.example.tbcworks.presentation.screens.pots.model.PotModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PotsViewModel @Inject constructor(
    private val getPotsUseCase: GetPotsUseCase,
    private val addPotUseCase: AddPotUseCase,
    private val deletePotUseCase: DeletePotUseCase,
    private val editPotUseCase: EditPotUseCase,
    private val addMoneyUseCase: AddMoneyToPotUseCase,
    private val withdrawMoneyUseCase: WithdrawMoneyFromPotUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : BaseViewModel<PotState, PotSideEffect, PotEvent>(initialState = PotState()) {

    fun onEvent(event: PotEvent) {
        when (event) {
            is PotEvent.LoadPots -> loadPots()
            is PotEvent.AddPot -> addPot(event)
            is PotEvent.DeletePot -> deletePot(event)
            is PotEvent.EditPot -> editPot(event)
            is PotEvent.AddMoney -> addMoney(event)
            is PotEvent.WithdrawMoney -> withdrawMoney(event)
        }
    }

    private fun loadPots() {
        val userId = getCurrentUserIdUseCase() ?: return handleUserNotLoggedIn()

        setState { copy(isLoading = true, error = null) }

        viewModelScope.launch {
            getPotsUseCase(userId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        setState {
                            copy(
                                pots = resource.data.map { it.toPresentation() },
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false, error = resource.message) }
                        sendSideEffect(PotSideEffect.ShowSnackBar(resource.message ?: "Unknown error"))
                    }
                    Resource.Loading -> setState { copy(isLoading = true) }
                }
            }
        }
    }

    private fun addPot(event: PotEvent.AddPot) {
        val userId = getCurrentUserIdUseCase() ?: return handleUserNotLoggedIn()

        val newPot = Pot(
            id = "",
            title = event.title,
            balance = 0.0,
            targetAmount = event.targetAmount
        )

        setState { copy(isLoading = true, error = null) }

        viewModelScope.launch {
            addPotUseCase(userId, newPot).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        sendSideEffect(PotSideEffect.ShowSnackBar("Pot added successfully"))
                        onEvent(PotEvent.LoadPots)
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false) }
                        sendSideEffect(PotSideEffect.ShowSnackBar(resource.message ?: "Failed to add pot"))
                    }
                    Resource.Loading -> setState { copy(isLoading = true) }
                }
            }
        }
    }

    private fun deletePot(event: PotEvent.DeletePot) {
        val userId = getCurrentUserIdUseCase() ?: return handleUserNotLoggedIn()
        viewModelScope.launch {
            deletePotUseCase(userId, event.pot.id).collect { handleUnitResource(it, "Pot deleted successfully") { loadPots() } }
        }
    }

    private fun editPot(event: PotEvent.EditPot) {
        val userId = getCurrentUserIdUseCase() ?: return handleUserNotLoggedIn()
        viewModelScope.launch {
            editPotUseCase(userId, event.pot.toDomain()).collect { handleUnitResource(it, "Pot updated successfully") { loadPots() } }
        }
    }

    private fun addMoney(event: PotEvent.AddMoney) {
        val userId = getCurrentUserIdUseCase() ?: return handleUserNotLoggedIn()
        viewModelScope.launch {
            addMoneyUseCase(userId, event.pot.id, event.amount).collect { handleUnitResource(it, "Money added successfully") { loadPots() } }
        }
    }

    private fun withdrawMoney(event: PotEvent.WithdrawMoney) {
        val userId = getCurrentUserIdUseCase() ?: return handleUserNotLoggedIn()
        viewModelScope.launch {
            withdrawMoneyUseCase(userId, event.pot.id, event.amount).collect { handleUnitResource(it, "Money withdrawn successfully") { loadPots() } }
        }
    }


    private fun handleUserNotLoggedIn() {
        setState { copy(isLoading = false) }
        sendSideEffect(PotSideEffect.ShowSnackBar("User not logged in"))
    }

    private fun handleError(message: String?) {
        setState { copy(isLoading = false, error = message) }
        sendSideEffect(PotSideEffect.ShowSnackBar(message ?: "Unknown error"))
    }

    private fun handleUnitResource(resource: Resource<Unit>, successMessage: String, onSuccess: () -> Unit) {
        when (resource) {
            is Resource.Success -> {
                sendSideEffect(PotSideEffect.ShowSnackBar(successMessage))
                onSuccess()
            }
            is Resource.Error -> handleError(resource.message)
            Resource.Loading -> setState { copy(isLoading = true) }
        }
    }
}
