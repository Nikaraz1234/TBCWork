package com.example.tbcworks.data.resource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class HandleResponse @Inject constructor() {
    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                Resource.Success(apiCall())
            } catch (e: HttpException) {
                Resource.Error(ERROR_HTTP)
            } catch (e: IOException) {
                Resource.Error(ERROR_NO_INTERNET)
            } catch (e: TimeoutCancellationException) {
                Resource.Error(ERROR_TIMEOUT)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: ERROR_UNKNOWN)
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