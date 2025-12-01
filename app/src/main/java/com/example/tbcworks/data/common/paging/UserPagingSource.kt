package com.example.tbcworks.data.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tbcworks.data.service.UserService
import com.example.tbcworks.data.dtos.UserResponseDto
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val api: UserService
) : PagingSource<Int, UserResponseDto.UserDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponseDto.UserDto> {
        val nextPage = params.key ?: 1
        return try {
            val response = api.getUsers(nextPage, params.loadSize)

            if (response.isSuccessful) {
                val users = response.body()?.data ?: emptyList()
                val totalPages = response.body()?.totalPages ?: nextPage

                LoadResult.Page(
                    data = users,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (nextPage >= totalPages || users.isEmpty()) null else nextPage + 1
                )
            } else {
                LoadResult.Error(Exception("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }



    override fun getRefreshKey(state: PagingState<Int, UserResponseDto.UserDto>): Int? = null
}
