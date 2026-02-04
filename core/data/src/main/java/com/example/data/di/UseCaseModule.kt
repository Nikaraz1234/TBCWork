package com.example.data.di

import com.example.domain.repository.DataStoreManager
import com.example.domain.usecase.datastore.GetPreferenceUseCase
import com.example.domain.usecase.datastore.RemovePreferenceUseCase
import com.example.domain.usecase.datastore.SetPreferenceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPreferenceUseCase(
        repo: DataStoreManager
    ): GetPreferenceUseCase = GetPreferenceUseCase(repo)

    @Provides
    fun provideSetPreferenceUseCase(
        repo: DataStoreManager
    ): SetPreferenceUseCase = SetPreferenceUseCase(repo)

    @Provides
    fun provideRemovePreferenceUseCase(
        repo: DataStoreManager
    ): RemovePreferenceUseCase = RemovePreferenceUseCase(repo)
}
