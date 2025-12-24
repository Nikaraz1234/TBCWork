package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.auth.sign_in.SignInRequestDto
import com.example.tbcworks.data.model.auth.sign_in.SignInResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("login")
    suspend fun signIn(@Body request: SignInRequestDto): SignInResponseDto
}