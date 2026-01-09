package com.example.challenge.data.common

import com.example.challenge.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <Dto : Any, Domain : Any> Flow<Resource<Dto>>.asResource(
    onSuccess: (Dto) -> Domain
): Flow<Resource<Domain>> {
    return this.map {
        when (it) {
            is Resource.Success -> Resource.Success(data = onSuccess(it.data))
            is Resource.Error -> Resource.Error(errorMessage = it.errorMessage)
            is Resource.Loading -> Resource.Loading(loading = it.loading)
        }
    }
}

