package com.example.tbcworks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tbcworks.userModel.User

class UserViewModel : ViewModel() {

    private val _users = MutableLiveData<MutableList<User>>(mutableListOf())
    val users: LiveData<MutableList<User>> get() = _users

    private val _deletedUsers = MutableLiveData(0)
    val deletedUsers: LiveData<Int> get() = _deletedUsers

    fun addUser(user: User) {
        val currentList = _users.value ?: mutableListOf()
        currentList.add(user)
        _users.value = currentList
    }

    fun updateUser(user: User) {
        val currentList = _users.value ?: mutableListOf()
        val index = currentList.indexOfFirst { it.email == user.email }
        if (index != -1) {
            currentList[index] = user
            _users.value = currentList
        }
    }

    fun deleteUser(email: String) {
        val currentList = _users.value ?: mutableListOf()
        val user = currentList.find { it.email == email }
        if (user != null) {
            currentList.remove(user)
            _users.value = currentList
            _deletedUsers.value = (_deletedUsers.value ?: 0) + 1
        }
    }
}