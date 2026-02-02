package com.example.mycomposeapp.ui.screen.form

import com.example.mycomposeapp.domain.usecase.form.GetFormUseCase
import com.example.mycomposeapp.domain.usecase.validation.EmptyFieldUseCase
import com.example.mycomposeapp.ui.common.BaseViewModel
import com.example.mycomposeapp.ui.screen.form.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val getFormUseCase: GetFormUseCase,
    private val emptyFieldUseCase: EmptyFieldUseCase
) : BaseViewModel<FormContract.State,FormContract.SideEffect, FormContract.Event>(
    initialState = FormContract.State()
){

    fun onEvent(event: FormContract.Event){
        when(event) {
            FormContract.Event.LoadForm -> loadForm()
            FormContract.Event.RegisterClicked -> register()
            is FormContract.Event.FieldValueChanged -> onFieldChanged(event)

        }
    }


    private fun onFieldChanged(event: FormContract.Event.FieldValueChanged) {
        setState {
            copy(
                fieldValues = fieldValues + (event.fieldId to event.value)
            )
        }
    }

    private fun register() {
        val requiredFields = uiState.value.form.fields
            .flatten()
            .filter { it.required == true }

        val invalid = requiredFields.firstOrNull { field ->
            !emptyFieldUseCase(uiState.value.fieldValues[field.fieldId])
        }

        if (invalid != null) {
            sendSideEffect(
                FormContract.SideEffect.ShowSnackBar("${invalid.hint} is required")
            )
            return
        }

        sendSideEffect(FormContract.SideEffect.ShowSnackBar("Registered"))
    }
    private fun loadForm(){
        handleResponse(
            apiCall = { getFormUseCase() },
            onSuccess = { form ->
                setState {
                    copy(
                        form = form.toPresentation(),
                        error = null,
                        isLoading = false
                    )
                }
            },
            onLoading = {
                setState { copy(isLoading = true) }
            },
            onError ={
                setState { copy(error = it, isLoading = false) }
                sendSideEffect(FormContract.SideEffect.ShowSnackBar(message = it))
            }
        )
    }
}