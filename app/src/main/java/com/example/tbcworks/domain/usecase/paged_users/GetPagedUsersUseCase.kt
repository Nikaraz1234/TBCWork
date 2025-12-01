package com.example.tbcworks.domain.usecase.paged_users

import androidx.paging.PagingData
import com.example.tbcworks.domain.model.GetUser
import com.example.tbcworks.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<PagingData<GetUser>> {
        return repository.getPagedUsers()
    }
}