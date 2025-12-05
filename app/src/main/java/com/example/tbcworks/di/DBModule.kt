package com.example.tbcworks.di

import android.content.Context
import androidx.room.Room
import com.example.tbcworks.data.local.AppDatabase
import com.example.tbcworks.data.local.dao.UserDao
import com.example.tbcworks.data.local.entity.UserEntity
import com.example.tbcworks.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
}