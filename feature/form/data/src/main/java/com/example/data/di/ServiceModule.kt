package com.example.data.di

import com.example.data.service.FormService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideFormService(
        retrofit: Retrofit
    ): FormService =
        retrofit.create(FormService::class.java)
}
