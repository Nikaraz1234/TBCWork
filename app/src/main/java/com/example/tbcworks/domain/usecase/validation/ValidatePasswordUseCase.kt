package com.example.tbcworks.domain.usecase.validation

import com.example.tbcworks.domain.Resource
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): Resource<Unit> {
        return if (password.length >= 6) {
            Resource.Success(Unit)
        } else {
            Resource.Error("Password must be at least 6 characters")
        }
    }
}
