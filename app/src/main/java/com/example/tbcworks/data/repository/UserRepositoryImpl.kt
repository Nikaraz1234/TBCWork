package com.example.tbcworks.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbcworks.data.common.paging.UserPagingSource
import com.example.tbcworks.domain.model.GetUser
import com.example.tbcworks.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import androidx.paging.map
import com.example.tbcworks.data.service.UserService
import com.example.tbcworks.data.mapper.toDomain
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserService
) : UserRepository {


    override fun getPagedUsers(): Flow<PagingData<GetUser>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(api) }
        ).flow.map { pagingData ->
            pagingData.map { dto ->
                dto.toDomain()
            }
        }
    }

}
