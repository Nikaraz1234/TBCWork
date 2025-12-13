package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.datastore.DataStoreManager
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.service.LoginService
import com.example.tbcworks.domain.repository.LoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService,
    private val handleResponse: HandleResponse,
    private val dataStore: DataStoreManager
) : LoginRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun login(email: String, password: String) = handleResponse.safeApiCall {
        val user = loginService.signIn(email, password)
        val token = loginService.getIdToken(user)

        dataStore.save("user_token", token)

        user
    }
}
