package com.example.tbcworks.domain.usecase.validation

import com.example.tbcworks.domain.validation.ValidationResult
import com.example.tbcworks.domain.validation.Validator
import javax.inject.Inject


class ValidateDepartmentSelectedUseCase @Inject constructor() : Validator<String> {

    override fun validate(value: String): ValidationResult {
        return if (value.isBlank()) {
            ValidationResult.Error("Please select a department")
        } else {
            ValidationResult.Success
        }
    }
}