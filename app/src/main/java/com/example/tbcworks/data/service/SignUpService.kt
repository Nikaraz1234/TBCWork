package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.auth.sign_up.SignUpRequestDto
import com.example.tbcworks.data.model.auth.sign_up.SignUpResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("signup")
    suspend fun signUp(@Body request: SignUpRequestDto): SignUpResponseDto
}