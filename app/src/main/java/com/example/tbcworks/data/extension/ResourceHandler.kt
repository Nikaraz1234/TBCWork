package com.example.tbcworks.data.extension

import com.example.tbcworks.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <Dto, Domain> Flow<Resource<Dto>>.asResource(mapper: (Dto) -> Domain): Flow<Resource<Domain>> = map { resource ->
    when (resource) {
        is Resource.Success -> Resource.Success(mapper(resource.data))
        is Resource.Error -> Resource.Error(resource.message)
        is Resource.Loading -> Resource.Loading
    }
}