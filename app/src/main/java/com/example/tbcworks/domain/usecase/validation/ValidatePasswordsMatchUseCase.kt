package com.example.tbcworks.domain.usecase.validation

import com.example.tbcworks.domain.validation.ValidationResult

class ValidatePasswordsMatchUseCase {

    fun validate(
        password: String,
        confirmPassword: String
    ): ValidationResult {
        return if (password != confirmPassword) {
            ValidationResult.Error("Passwords do not match")
        } else {
            ValidationResult.Success
        }
    }
}
