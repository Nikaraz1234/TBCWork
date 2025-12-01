package com.example.tbcworks.data.repository

import com.example.tbcworks.data.service.RegisterService
import com.example.tbcworks.data.dtos.RegisterRequestDto
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.dtos.RegisterResponseDto
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.GetRegisterResponse
import com.example.tbcworks.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


class RegisterRepositoryImpl @Inject constructor(
    private val api: RegisterService,
    private val handleResponse: HandleResponse,
) : RegisterRepository {

    override suspend fun register(
        email: String,
        password: String
    ): Flow<Resource<GetRegisterResponse>> {
        return handleResponse.safeApiCall {
            api.register(RegisterRequestDto(email, password))
        }.asResource { response: Response<RegisterResponseDto> ->
            val body = response.body() ?: throw Exception(REGISTRATION_FAILED)
            body.toDomain()
        }
    }

    companion object {
        private const val REGISTRATION_FAILED = "Registration failed"
    }
}
