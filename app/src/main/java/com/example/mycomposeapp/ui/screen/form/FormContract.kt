package com.example.mycomposeapp.ui.screen.form

import com.example.mycomposeapp.ui.screen.form.model.FormUi

object FormContract {
    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val form: FormUi = FormUi.empty(),
        val fieldValues: Map<Int, String> = emptyMap()

    )

    sealed interface SideEffect{
        data class ShowSnackBar(val message: String) : SideEffect
    }
    sealed interface Event {
        data object LoadForm : Event
        data object RegisterClicked: Event
        data class FieldValueChanged(val fieldId: Int, val value: String) : Event
    }
}