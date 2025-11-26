package com.example.tbcworks.data.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tbcworks.data.auth.api.UserApi
import com.example.tbcworks.data.auth.models.User

class UserPagingSource(
    private val api: UserApi
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val nextPage = params.key ?: 1
        return try {
            val response = api.getUsers(nextPage, params.loadSize)

            if (response.isSuccessful) {
                val users = response.body()?.data ?: emptyList()
                val totalPages = response.body()?.total_pages ?: nextPage

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



    override fun getRefreshKey(state: PagingState<Int, User>): Int? = null
}
