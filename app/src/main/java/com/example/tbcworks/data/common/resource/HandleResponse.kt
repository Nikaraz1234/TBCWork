package com.example.tbcworks.data.common.resource

import com.example.tbcworks.domain.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

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
                    is retrofit2.HttpException -> Resource.Error("Server Error: ${e.code()}")
                    else -> Resource.Error("Unexpected Error: ${e.localizedMessage}")
                }
            )
        }

    }

}

