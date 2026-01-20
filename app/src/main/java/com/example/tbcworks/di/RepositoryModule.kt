package com.example.tbcworks.di

import com.example.tbcworks.data.common.HandleResponse
import com.example.tbcworks.data.repository.CategoryRepositoryImpl
import com.example.tbcworks.data.repository.LoginRepositoryImpl
import com.example.tbcworks.data.repository.ProductRepositoryImpl
import com.example.tbcworks.data.repository.RegisterRepositoryImpl
import com.example.tbcworks.data.service.CategoryService
import com.example.tbcworks.data.service.ProductService
import com.example.tbcworks.domain.repository.CategoryRepository
import com.example.tbcworks.domain.repository.LoginRepository
import com.example.tbcworks.domain.repository.ProductRepository
import com.example.tbcworks.domain.repository.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideHandleResponse(): HandleResponse = HandleResponse()

    @Provides
    @Singleton
    fun provideLoginRepository(
        firebaseAuth: FirebaseAuth,
        handleResponse: HandleResponse
    ): LoginRepository {
        return LoginRepositoryImpl(firebaseAuth, handleResponse)
    }
    @Provides
    @Singleton
    fun provideRegisterRepository(
        firebaseAuth: FirebaseAuth,
        handleResponse: HandleResponse
    ): RegisterRepository {
        return RegisterRepositoryImpl(firebaseAuth, handleResponse)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        service: ProductService,
        handleResponse: HandleResponse
    ): ProductRepository {
        return ProductRepositoryImpl(service, handleResponse)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        service: CategoryService,
        handleResponse: HandleResponse
    ): CategoryRepository {
        return CategoryRepositoryImpl(service, handleResponse)
    }
}