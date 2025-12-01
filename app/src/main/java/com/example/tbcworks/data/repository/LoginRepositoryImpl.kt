package com.example.tbcworks.data.repository

import com.example.tbcworks.data.service.LoginService
import com.example.tbcworks.data.dtos.LoginRequestDto
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.GetLoginResponse
import com.example.tbcworks.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(
    private val api: LoginService,
    private val handleResponse: HandleResponse
) : LoginRepository {

    override suspend fun login(email: String, password: String): Flow<Resource<GetLoginResponse>> {
        return handleResponse.safeApiCall {
            api.login(LoginRequestDto(email, password))
        }.asResource {response ->
            val body = response.body() ?: throw Exception("Empty response")
            body.toDomain()
        }
    }


    companion object {
        private const val INVALID_CREDENTIALS = "Invalid credentials"
    }
}