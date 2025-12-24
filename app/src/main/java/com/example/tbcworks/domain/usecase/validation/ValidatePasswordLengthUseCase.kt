package com.example.tbcworks.domain.usecase.validation

import com.example.tbcworks.domain.validation.ValidationResult
import com.example.tbcworks.domain.validation.Validator
import javax.inject.Inject

class ValidatePasswordLengthUseCase @Inject constructor() : Validator<String> {

    private val minLength = 8

    override fun validate(value: String): ValidationResult {
        return if (value.length < minLength) {
            ValidationResult.Error(
                "Password must be at least $minLength characters"
            )
        } else {
            ValidationResult.Success
        }
    }
}


