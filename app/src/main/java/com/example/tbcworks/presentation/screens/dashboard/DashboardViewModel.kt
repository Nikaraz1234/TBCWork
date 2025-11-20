package com.example.tbcworks.presentation.screens.dashboard
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.auth.api.UserApi
import com.example.tbcworks.data.auth.models.User
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.common.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel  @Inject constructor(
    private val userApi: UserApi,
    private val handleResponse: HandleResponse
): ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users


    fun onEvent(event: DashboardEvent){
        when (event){
            is DashboardEvent.FetchUsers ->
                fetchUsers()
        }
    }

    private fun fetchUsers() {
        viewModelScope.launch {
           when( val response = handleResponse.safeApiCall { userApi.getUsers()}){
               is Resource.Success -> _users.value = response.data.data
               is Resource.Error -> println("Error: ${response.message}")
               is Resource.Loading -> { }
           }

        }
    }
}