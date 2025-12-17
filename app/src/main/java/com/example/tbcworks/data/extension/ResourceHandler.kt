package com.example.tbcworks.data.extension

import com.example.tbcworks.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


inline fun <T, R> Resource<T>.asResource(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(message)
        Resource.Loading -> Resource.Loading
    }
}
fun <T> Flow<Resource<T>>.toUnitResource(): Flow<Resource<Unit>> =
    map { resource ->
        resource.asResource { _: T -> Unit }
    }
