package com.example.mycomposeapp.ui.screen.form.mapper

import com.example.mycomposeapp.domain.model.Form
import com.example.mycomposeapp.ui.screen.form.model.FormUi

fun Form.toPresentation(): FormUi {
    return FormUi(
        fields = fields.map { list -> list.map {
            it.toPresentation()
        } }
    )
}

fun Form.Field.toPresentation(): FormUi.FieldUi {
    return FormUi.FieldUi(
        fieldId = fieldId,
        hint = hint,
        fieldType = fieldType,
        keyboard = keyboard,
        required = required ?: false,
        isActive = isActive,
        icon = icon
    )
}
