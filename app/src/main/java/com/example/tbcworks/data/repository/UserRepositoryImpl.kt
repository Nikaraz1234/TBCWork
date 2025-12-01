package com.example.tbcworks.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbcworks.data.api.UserApi
import com.example.tbcworks.data.common.paging.UserPagingSource
import com.example.tbcworks.domain.model.GetUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi
) {
    fun getPagedUsers(): Flow<PagingData<GetUser>> =
        Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(api) }
        ).flow

    override fun getPagedUsers(): Flow<PagingData<GetUser>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UsersPagingSource(api) }
        ).flow.map { pagingData ->
            pagingData.map { dto ->
                dto.toDomain()
            }
        }
    }

}
