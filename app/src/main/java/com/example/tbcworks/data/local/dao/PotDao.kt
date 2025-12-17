package com.example.tbcworks.data.local.dao

import androidx.room.*
import com.example.tbcworks.data.local.entity.PotEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPot(pot: PotEntity)

    @Update
    suspend fun editPot(pot: PotEntity)

    @Delete
    suspend fun deletePot(pot: PotEntity)

    @Query("SELECT * FROM pots WHERE userId = :userId")
    fun getPots(userId: String): Flow<List<PotEntity>>

    @Query("SELECT COUNT(*) FROM pots WHERE userId = :userId")
    fun getPotsCount(userId: String): Flow<Int>

    @Query("SELECT * FROM pots WHERE synced = 0")
    fun getUnsyncedPots(): Flow<List<PotEntity>>

}
