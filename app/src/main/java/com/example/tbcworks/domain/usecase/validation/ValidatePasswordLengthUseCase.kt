package com.example.tbcworks.domain.usecase.validation

import com.example.tbcworks.domain.validation.ValidationResult

class ValidatePasswordUseCase(
    private val minLength: Int = 8
) {

    fun execute(password: String): ValidationResult {
        return if (password.length < minLength) {
            ValidationResult(
                isValid = false,
                errorMessage = "Password must be at least $minLength characters"
            )
        } else {
            ValidationResult(isValid = true)
        }
    }
}
