package com.example.tbcworks.domain.usecase.validation

import com.example.tbcworks.domain.validation.ValidationResult
import com.example.tbcworks.domain.validation.Validator
import javax.inject.Inject

class ValidateNotEmptyUseCase @Inject constructor() : Validator<String> {

    override fun validate(value: String): ValidationResult {
        return if (value.isBlank()) {
            ValidationResult.Error("EMPTY")
        } else {
            ValidationResult.Success
        }
    }
}
