package com.example.tbcworks.domain.usecase.validation

import com.example.tbcworks.domain.Resource
import javax.inject.Inject

class ValidateNotEmptyUseCase @Inject constructor() {
    operator fun invoke(email: String, password: String): Resource<Unit> {
        return if (email.isNotBlank() && password.isNotBlank()) {
            Resource.Success(Unit)
        } else {
            Resource.Error("Please fill all fields")
        }
    }
}
