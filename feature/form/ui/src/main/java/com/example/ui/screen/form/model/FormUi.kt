package com.example.ui.screen.form.model

data class FormUi(
    val fields: List<List<FieldUi>>
){
    data class FieldUi(
        val fieldId: Int,
        val hint: String,
        val fieldType: String,
        val keyboard: String? = null,
        val required: Boolean? = null,
        val isActive: Boolean,
        val icon: String
    )
    companion object {
        fun empty(): FormUi = FormUi(fields = emptyList())
    }

}
