package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.datastore.DataStoreKeys
import com.example.tbcworks.data.common.datastore.DataStoreManager
import com.example.tbcworks.data.common.firestore.FirestoreFields
import com.example.tbcworks.data.common.firestore.FirestoreHelper
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.local.datasource.UserLocalDataSource
import com.example.tbcworks.data.local.entity.UserEntity
import com.example.tbcworks.data.remote.service.LoginService
import com.example.tbcworks.domain.repository.LoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService,
    private val handleResponse: HandleResponse,
    private val dataStore: DataStoreManager,
    private val firestoreHelper: FirestoreHelper,
    private val userLocalDataSource: UserLocalDataSource
) : LoginRepository {

    override fun login(email: String, password: String) = handleResponse.safeApiCall {
        val user = loginService.signIn(email, password)
        val token = loginService.getIdToken(user)
        dataStore.save(DataStoreKeys.USER_TOKEN, token)

        val userSnapshot = firestoreHelper.userRef(user.uid).get().await()
        val balance = userSnapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0
        val emailFromFirestore = userSnapshot.getString(FirestoreFields.EMAIL) ?: email

        userLocalDataSource.addOrUpdateUser(
            UserEntity(
                id = user.uid,
                email = emailFromFirestore,
                balance = balance
            )
        )

        user
    }

    override suspend fun logout() {
        dataStore.remove(DataStoreKeys.USER_TOKEN)
        loginService.signOut()
    }
}

