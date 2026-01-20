package com.example.tbcworks.data.common

import com.example.tbcworks.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HandleResponse @Inject constructor() {

    fun <T> safeApiCall(apiCall: suspend () -> T): Flow<Resource<T>> = flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(apiCall()))
        } catch (e: Exception) {
            emit(
                when (e) {
                    is java.net.UnknownHostException -> Resource.Error("No Internet Connection")
                    is HttpException -> Resource.Error("Server Error: ${e.code()}")
                    else -> Resource.Error("Unexpected Error: ${e.localizedMessage}")
                }
            )
        }

    }

}