package com.example.tbcworks.data.local.datasource

import com.example.tbcworks.data.local.dao.PotDao
import com.example.tbcworks.data.local.entity.PotEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PotLocalDataSource @Inject constructor(
    private val potDao: PotDao
) {
    fun getPots(userId: String): Flow<List<PotEntity>> = potDao.getPots(userId)
    fun getPotsCount(userId: String): Flow<Int> = potDao.getPotsCount(userId)
    suspend fun addPot(pot: PotEntity) = potDao.addPot(pot)
    suspend fun editPot(pot: PotEntity) = potDao.editPot(pot)
    suspend fun deletePot(pot: PotEntity) = potDao.deletePot(pot)

    fun getUnsyncedPots(): Flow<List<PotEntity>> = potDao.getUnsyncedPots()
    suspend fun markAsSynced(pot: PotEntity) = potDao.editPot(pot.copy(synced = true))

}

