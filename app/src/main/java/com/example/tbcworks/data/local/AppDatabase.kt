package com.example.tbcworks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tbcworks.data.local.dao.PotDao
import com.example.tbcworks.data.local.dao.TransactionDao
import com.example.tbcworks.data.local.dao.UserDao
import com.example.tbcworks.data.local.entity.PotEntity
import com.example.tbcworks.data.local.entity.TransactionEntity
import com.example.tbcworks.data.local.entity.UserEntity

@Database(
    entities = [PotEntity::class, TransactionEntity::class, UserEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun potDao(): PotDao
    abstract fun transactionDao(): TransactionDao
    abstract fun userDao() : UserDao
}
