package com.example.data.repository

import com.example.data.common.HandleResponse
import com.example.data.mapper.toDomain
import com.example.data.extension.asResource
import com.example.data.service.FormService
import com.example.domain.Resource
import com.example.domain.model.Form
import com.example.domain.repository.FormRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FormRepositoryImpl @Inject constructor(
    val service: FormService,
    val handleResponse: HandleResponse
) : FormRepository {
    override fun getForm(): Flow<Resource<Form>> {
        return handleResponse.safeApiCall {
            service.getForm()
        }.map { resource ->
            resource.asResource { dto ->
                dto.toDomain()
            }
        }
    }

}