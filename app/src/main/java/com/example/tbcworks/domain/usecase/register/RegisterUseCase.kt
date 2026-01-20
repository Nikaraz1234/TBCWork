package com.example.tbcworks.domain.usecase.register

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.User
import com.example.tbcworks.domain.repository.RegisterRepository
import com.example.tbcworks.domain.usecase.validation.ValidateEmailUseCase
import com.example.tbcworks.domain.usecase.validation.ValidateNotEmptyUseCase
import com.example.tbcworks.domain.usecase.validation.ValidatePasswordUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val validateNotEmptyUseCase: ValidateNotEmptyUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {
    operator fun invoke(email: String, password: String): Flow<Resource<User>> = flow {
        validateNotEmptyUseCase(email, password).let {
            if (it is Resource.Error) {
                emit(Resource.Error(it.message))
                return@flow
            }
        }

        validateEmailUseCase(email).let {
            if (it is Resource.Error) {
                emit(Resource.Error(it.message))
                return@flow
            }
        }

        validatePasswordUseCase(password).let {
            if (it is Resource.Error) {
                emit(Resource.Error(it.message))
                return@flow
            }
        }
        emit(Resource.Loading)
        val result = registerRepository.register(email, password)
        emitAll(result)
    }
}
