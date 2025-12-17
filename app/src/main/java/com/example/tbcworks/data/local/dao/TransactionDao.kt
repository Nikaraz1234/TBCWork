package com.example.tbcworks.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tbcworks.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("""
        SELECT * FROM transactions 
        WHERE senderId = :userId 
        ORDER BY date DESC
    """)
    fun getTransactions(userId: String): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<TransactionEntity>)

    @Query("SELECT * FROM transactions WHERE synced = 0")
    fun getUnsyncedTransactions(): Flow<List<TransactionEntity>>

    @Update
    suspend fun editTransaction(transaction: TransactionEntity)
}
