package com.example.domain.model

data class Form(
    val fields: List<List<Field>>
){
    data class Field(
        val fieldId: Int,
        val hint: String,
        val fieldType: String,
        val keyboard: String? = null,
        val required: Boolean? = null,
        val isActive: Boolean,
        val icon: String
    )
}