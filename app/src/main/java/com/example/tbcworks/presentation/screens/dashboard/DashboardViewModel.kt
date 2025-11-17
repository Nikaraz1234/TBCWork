package com.example.tbcworks.presentation.screens.dashboard
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.auth.ApiClient
import com.example.tbcworks.data.auth.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DashboardViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = ApiClient.userApi.getUsers()
                _users.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}