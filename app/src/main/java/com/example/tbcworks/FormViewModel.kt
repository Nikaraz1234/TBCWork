package com.example.tbcworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class FormViewModel : ViewModel() {

    private val _fieldGroups = MutableStateFlow<List<FieldGroup>>(emptyList())
    val fieldGroups: StateFlow<List<FieldGroup>> = _fieldGroups
    private val _fieldValues = MutableStateFlow<Map<Int, String>>(emptyMap())
    val fieldValues: StateFlow<Map<Int, String>> = _fieldValues.asStateFlow()

    private val _errorFields = mutableListOf<String?>()
    val errorFields: List<String?> get() = _errorFields

    fun getFieldValue(fieldId: Int): String {
        return _fieldValues.value[fieldId].orEmpty()
    }
    fun addFieldValue(){
        viewModelScope.launch {
        }
    }


    fun validateFields(): Boolean {
        _errorFields.clear()

        fieldGroups.value.forEach { group ->
            group.fields.forEach { field ->
                val value = _fieldValues.value[field.field_id] // lookup by unique ID
                if (field.required && value.isNullOrBlank()) {
                    _errorFields.add(field.hint)
                }
            }
        }

        return _errorFields.isEmpty()
    }

    fun updateFieldValue(fieldId: Int, value: String) {
        _fieldValues.value = _fieldValues.value.toMutableMap().apply {
            put(fieldId, value)
        }
    }


    fun parseJson(jsonString: String) {
        viewModelScope.launch {
            val outerArray: List<List<Field>> = Json.decodeFromString(jsonString)
            val groups = outerArray.map { FieldGroup(it) }
            _fieldGroups.value = groups
        }
    }



}