package com.example.tbcworks.data.local.dao

import androidx.room.*
import com.example.tbcworks.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: String): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdateUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): Flow<UserEntity?>
    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Transaction
    suspend fun deleteUser(userId: String) {
        getUserById(userId).firstOrNull()?.let { deleteUser(it) }
    }
}