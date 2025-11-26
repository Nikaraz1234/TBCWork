package com.example.tbcworks.data.common.resource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class HandleResponse @Inject constructor() {

    fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): Flow<Resource<T>> = flow {
        try {
            emit(Resource.Success(apiCall()))
        } catch (e: HttpException) {
            emit(Resource.Error(ERROR_HTTP))
        } catch (e: IOException) {
            emit(Resource.Error(ERROR_NO_INTERNET))
        } catch (e: TimeoutCancellationException) {
            emit(Resource.Error(ERROR_TIMEOUT))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ERROR_UNKNOWN))
        }
    }.flowOn(dispatcher)
    companion object {
        const val ERROR_HTTP = "HTTP error"
        const val ERROR_NO_INTERNET = "No internet connection"
        const val ERROR_TIMEOUT = "Request timed out"
        const val ERROR_UNKNOWN = "Unknown error"
    }
}