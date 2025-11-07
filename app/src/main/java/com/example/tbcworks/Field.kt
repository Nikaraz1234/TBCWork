package com.example.tbcworks

import kotlinx.serialization.Serializable


@Serializable
data class Field(
    val field_id: Int,
    val hint: String?,
    val field_type: String,
    val keyboard: String? = null,
    val required: Boolean = false,
    val is_active: Boolean = true,
    val icon: String? = null
)


data class FieldGroup(
    val fields: List<Field>
)

