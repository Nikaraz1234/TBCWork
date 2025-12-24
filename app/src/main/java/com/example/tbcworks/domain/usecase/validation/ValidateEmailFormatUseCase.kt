package com.example.tbcworks.domain.usecase.validation

import com.example.tbcworks.domain.validation.ValidationResult
import com.example.tbcworks.domain.validation.Validator
import javax.inject.Inject

class ValidateEmailFormatUseCase @Inject constructor(): Validator<String> {

    private val emailRegex =
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    override fun validate(value: String): ValidationResult {
        return if (!emailRegex.matches(value)) {
            ValidationResult.Error("Invalid email format")
        } else {
            ValidationResult.Success
        }
    }
}
