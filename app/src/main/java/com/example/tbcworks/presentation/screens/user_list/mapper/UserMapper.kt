package com.example.tbcworks.presentation.screens.user_list.mapper

import com.example.tbcworks.domain.model.User
import com.example.tbcworks.presentation.screens.user_list.UserModel

fun User.toPresentation(): UserModel {
    return UserModel(
        id = id,
        fullName = fullName,
        email = email,
        activationStatus = activationStatus,
        lastActiveDescription = lastActiveDescription,
        lastActiveEpoch = lastActiveEpoch,
        profileImageUrl = profileImageUrl,
        cachedImagePath = null,
    )
}
