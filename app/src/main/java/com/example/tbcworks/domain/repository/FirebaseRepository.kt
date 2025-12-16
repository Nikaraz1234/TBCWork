package com.example.tbcworks.domain.repository

interface FirebaseRepository {
    fun getCurrentUserId(): String?
    fun getCurrentUserEmail(): String?
}