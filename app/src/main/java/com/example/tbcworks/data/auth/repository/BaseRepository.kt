package com.example.tbcworks.data.auth.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(val message: String) : ResultWrapper<Nothing>()
}

abstract class BaseRepository {

    protected suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall())
            } catch (e: HttpException) {
                ResultWrapper.Error(ERROR_HTTP)
            } catch (e: IOException) {
                ResultWrapper.Error(ERROR_NO_INTERNET)
            } catch (e: TimeoutCancellationException) {
                ResultWrapper.Error(ERROR_TIMEOUT)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                ResultWrapper.Error(e.localizedMessage ?: ERROR_UNKNOWN)
            }
        }
    }

    companion object {
        const val ERROR_HTTP = "HTTP error"
        const val ERROR_NO_INTERNET = "No internet connection"
        const val ERROR_TIMEOUT = "Request timed out"
        const val ERROR_UNKNOWN = "Unknown error"
    }
}