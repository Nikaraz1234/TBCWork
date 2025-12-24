package com.example.tbcworks.di

import com.example.tbcworks.domain.usecase.validation.ValidatePasswordsMatchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ValidationModule {

    @Provides
    fun provideValidatePasswordsMatchUseCase(): ValidatePasswordsMatchUseCase {
        return ValidatePasswordsMatchUseCase()
    }

    // Provide other validation use cases similarly
}
