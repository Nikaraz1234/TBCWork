package com.example.tbcworks.di

import android.content.Context
import androidx.room.Room
import com.example.tbcworks.data.local.AppDatabase
import com.example.tbcworks.data.local.dao.PotDao
import com.example.tbcworks.data.local.dao.TransactionDao
import com.example.tbcworks.data.local.dao.UserDao
import com.example.tbcworks.data.local.datasource.PotLocalDataSource
import com.example.tbcworks.data.local.datasource.TransactionLocalDataSource
import com.example.tbcworks.data.local.datasource.UserLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePotDao(database: AppDatabase): PotDao {
        return database.potDao()
    }
    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideUserLocalDataSource(userDao: UserDao): UserLocalDataSource {
        return UserLocalDataSource(userDao)
    }

    @Provides
    fun providePotLocalDataSource(potDao: PotDao): PotLocalDataSource {
        return PotLocalDataSource(potDao)
    }

    @Provides
    fun provideTransactionLocalDataSource(transactionDao: TransactionDao): TransactionLocalDataSource {
        return TransactionLocalDataSource(transactionDao)
    }

}