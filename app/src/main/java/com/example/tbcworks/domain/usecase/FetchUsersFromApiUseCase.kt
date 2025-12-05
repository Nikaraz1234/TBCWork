package com.example.tbcworks.domain.usecase

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.User
import com.example.tbcworks.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUsersFromApiUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(hasInternet: Boolean) {
        if (hasInternet) {
            repository.fetchUsersFromApi()
        }
    }
}