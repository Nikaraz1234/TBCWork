package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.datastore.DataStoreKeys
import com.example.tbcworks.data.common.datastore.DataStoreManager
import com.example.tbcworks.data.common.firestore.FirestoreFields
import com.example.tbcworks.data.common.firestore.FirestoreHelper
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.local.datasource.UserLocalDataSource
import com.example.tbcworks.data.local.entity.UserEntity
import com.example.tbcworks.data.remote.service.SignUpService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.SignUpRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpRepositoryImpl @Inject constructor(
    private val signUpService: SignUpService,
    private val firestoreHelper: FirestoreHelper,
    private val dataStore: DataStoreManager,
    private val handleResponse: HandleResponse,
    private val userLocalDataSource: UserLocalDataSource
) : SignUpRepository {

    override fun signUp(email: String, password: String): Flow<Resource<FirebaseUser>> =
        handleResponse.safeApiCall {
            withContext(kotlinx.coroutines.Dispatchers.IO) {

                val user = signUpService.createUser(email, password)

                val token = signUpService.getIdToken(user)
                dataStore.save(DataStoreKeys.USER_TOKEN, token)

                val userData = mapOf(
                    FirestoreFields.UID to user.uid,
                    FirestoreFields.EMAIL to email,
                    FirestoreFields.BALANCE to 0.0
                )

                firestoreHelper.userRef(user.uid)
                    .set(userData)
                    .await()

                userLocalDataSource.addOrUpdateUser(
                    UserEntity(
                        id = user.uid,
                        email = email,
                        balance = 0.0
                    )
                )

                user
            }
        }

}

