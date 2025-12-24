package com.example.tbcworks.domain.usecase.validation

import com.example.tbcworks.domain.validation.ValidationResult
import com.example.tbcworks.domain.validation.Validator
import javax.inject.Inject

class ValidateTermsAcceptedUseCase @Inject constructor(): Validator<Boolean> {

    override fun validate(value: Boolean): ValidationResult {
        return if (!value) {
            ValidationResult.Error("You must accept the terms")
        } else {
            ValidationResult.Success
        }
    }
}