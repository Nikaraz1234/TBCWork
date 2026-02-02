package com.example.mycomposeapp.data.mapper

import com.example.mycomposeapp.data.dto.FieldDto
import com.example.mycomposeapp.domain.model.Form


fun FieldDto.toDomain(): Form.Field =
    Form.Field(
        fieldId = fieldId,
        hint = hint,
        fieldType = fieldType,
        keyboard = keyboard,
        required = required,
        isActive = isActive,
        icon = icon
    )

fun List<List<FieldDto>>.toDomain(): Form =
    Form(
        fields = map { section ->
            section.map { it.toDomain() }
        }
    )