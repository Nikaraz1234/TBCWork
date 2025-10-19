package com.example.tbcworks

class UserManager {
    val users : MutableList<User> = mutableListOf()
    var deletedUsers = 0

    fun addUser(user: User) {
        users.add(user)
    }

    fun updateUser(user: User) {
        val index = users.indexOfFirst { it.email == user.email }
        if (index != -1) {
            users[index] = user
        }
    }
    fun deleteUser(email: String) {
        val user = users.find { it.email == email }
        users.remove(user)
        deletedUsers++
    }
}