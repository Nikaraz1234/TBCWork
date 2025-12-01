package com.example.tbcworks.domain.repository

import androidx.paging.PagingData
import com.example.tbcworks.domain.model.GetUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getPagedUsers(): Flow<PagingData<GetUser>>
}