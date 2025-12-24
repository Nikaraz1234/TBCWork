package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.model.auth.sign_in.SignInRequestDto
import com.example.tbcworks.data.service.SignInService
import com.example.tbcworks.domain.model.auth.AuthToken
import com.example.tbcworks.domain.model.auth.SignIn
import com.example.tbcworks.domain.repository.SignInRepository
import com.example.tbcworks.domain.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val api: SignInService,
    private val handleResponse: HandleResponse
) : SignInRepository {

    override fun signIn(params: SignIn): Flow<Resource<AuthToken>> = flow {
        emitAll(
            handleResponse.safeApiCall {
                api.signIn(SignInRequestDto(params.email, params.password))
            }.map { resource ->
                resource.asResource { it.toDomain() }
            }.flowOn(Dispatchers.IO)
        )
    }
}

