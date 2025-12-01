package com.example.tbcworks.data.mapper


import com.example.tbcworks.data.common.dataStore.UserData
import com.example.tbcworks.domain.model.GetUserData

fun UserData.toDomain(): GetUserData = GetUserData(
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email
)