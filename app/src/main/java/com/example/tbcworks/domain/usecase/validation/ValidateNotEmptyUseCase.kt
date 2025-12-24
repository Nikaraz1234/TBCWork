package com.example.tbcworks.domain.usecase.validation

import com.example.tbcworks.domain.validation.ValidationResult

class ValidateNotEmptyUseCase(
    private val fieldName: String
) : Validator<String> {

    override fun validate(value: String): ValidationResult {
        return if (value.isBlank()) {
            ValidationResult.Error("$fieldName cannot be empty")
        } else {
            ValidationResult.Success
        }
    }
}