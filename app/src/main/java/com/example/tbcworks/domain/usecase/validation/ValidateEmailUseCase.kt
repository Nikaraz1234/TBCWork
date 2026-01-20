package com.example.tbcworks.domain.usecase.validation

import android.util.Patterns
import com.example.tbcworks.domain.Resource
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): Resource<Unit> {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Resource.Success(Unit)
        } else {
            Resource.Error("Invalid email format")
        }
    }
}
