package com.example.tbcworks.presentation.screens.pots

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.pots.AddPotUseCase
import com.example.tbcworks.domain.usecase.pots.GetPotsUseCase
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
    private val addPotUseCase: AddPotUseCase
) : BaseViewModel<PotState, PotSideEffect, PotEvent>(initialState = PotState()) {

    fun onEvent(intent: PotEvent) {
        when (intent) {
            is PotEvent.LoadPots -> loadPots()
            is PotEvent.AddPot -> addPot(intent)
        }
    }

    private fun loadPots() {
        setState { copy(isLoading = true, error = null) }
        viewModelScope.launch {
            getPotsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        println("Pots loaded: ${resource.data}")
                        setState {
                            copy(
                                pots = resource.data.map { it.toPresentation() },
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        println("Error loading pots: ${resource.message}")
                        setState { copy(isLoading = false, error = resource.message) }
                        sendSideEffect(PotSideEffect.ShowSnackBar(resource.message ?: "Unknown error"))
                    }
                    Resource.Loading -> setState { copy(isLoading = true) }
                }
            }
        }
    }

    private fun addPot(event: PotEvent.AddPot) {
        val newPot = PotModel(
            id = UUID.randomUUID().toString(),
            title = event.title,
            amount = 0.0,
            progressPercent = 0.0,
            targetAmount = event.targetAmount
        )

        setState { copy(isLoading = true, error = null) }
        viewModelScope.launch {
            addPotUseCase(newPot.toDomain()).collect { resource ->
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
}



