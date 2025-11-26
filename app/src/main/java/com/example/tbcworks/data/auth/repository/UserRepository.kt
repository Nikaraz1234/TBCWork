package com.example.tbcworks.data.auth.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbcworks.data.auth.api.UserApi
import com.example.tbcworks.data.auth.models.User
import com.example.tbcworks.data.common.paging.UserPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi
) {
    fun getPagedUsers(): Flow<PagingData<User>> =
        Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(api) }
        ).flow
}
