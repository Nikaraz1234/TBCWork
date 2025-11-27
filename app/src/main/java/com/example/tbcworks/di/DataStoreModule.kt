package com.example.tbcworks.di

import com.example.tbcworks.data.common.dataStore.UserData
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.tbcworks.data.common.dataStore.TokenDataStore
import com.example.tbcworks.data.common.dataStore.UserDataSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_DATA_FILE_NAME = "user_data.pb"

val Context.userDataStore: DataStore<UserData> by dataStore(
    fileName = USER_DATA_FILE_NAME,
    serializer = UserDataSerializer
)
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideTokenDataStore(@ApplicationContext context: Context): TokenDataStore {
        return TokenDataStore(context)
    }

    @Provides
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context): DataStore<UserData> {
        return context.userDataStore
    }

}