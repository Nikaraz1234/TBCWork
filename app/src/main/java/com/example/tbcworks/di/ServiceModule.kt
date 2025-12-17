package com.example.tbcworks.di

import com.example.tbcworks.data.common.firestore.AuthHelper
import com.example.tbcworks.data.common.firestore.FirestoreHelper
import com.example.tbcworks.data.remote.service.PotService
import com.example.tbcworks.data.remote.service.UserService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providePotService(firestore: FirestoreHelper): PotService = PotService(firestore)

    @Provides
    @Singleton
    fun provideUserService(firestore: FirestoreHelper, auth: AuthHelper): UserService =
        UserService(firestore, auth)
}
