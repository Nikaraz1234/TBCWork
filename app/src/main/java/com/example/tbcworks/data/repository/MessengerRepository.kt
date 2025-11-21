package com.example.tbcworks.data.repository

import com.example.tbcworks.data.api.MessengerApi
import com.example.tbcworks.data.models.User
import com.example.tbcworks.data.resource.HandleResponse
import com.example.tbcworks.data.resource.Resource
import javax.inject.Inject

class MessengerRepository @Inject constructor(
    private val api: MessengerApi,
    private val handleResponse: HandleResponse
) {
    suspend fun getUsers(): Resource<List<User>> {
        return handleResponse.safeApiCall {
            api.getUsers()
        }
    }
}