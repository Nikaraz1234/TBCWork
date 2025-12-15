package com.example.tbcworks.di

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.repository.FirebaseRepositoryImpl
import com.example.tbcworks.data.repository.LoginRepositoryImpl
import com.example.tbcworks.data.repository.PotRepositoryImpl
import com.example.tbcworks.data.repository.SignUpRepositoryImpl
import com.example.tbcworks.data.repository.TransactionRepositoryImpl
import com.example.tbcworks.domain.repository.FirebaseRepository
import com.example.tbcworks.domain.repository.LoginRepository
import com.example.tbcworks.domain.repository.PotRepository
import com.example.tbcworks.domain.repository.SignUpRepository
import com.example.tbcworks.domain.repository.TransactionRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPotRepository(
        impl: PotRepositoryImpl
    ): PotRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindSignUpRepository(
        impl: SignUpRepositoryImpl
    ): SignUpRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseRepository(
        impl: FirebaseRepositoryImpl
    ): FirebaseRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        impl: TransactionRepositoryImpl
    ): TransactionRepository
}
