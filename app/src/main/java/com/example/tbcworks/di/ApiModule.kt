package com.example.tbcworks.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.example.tbcworks.BuildConfig
import com.example.tbcworks.data.service.EventService
import com.example.tbcworks.data.service.SignInService
import com.example.tbcworks.data.service.SignUpService

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://reqres.in/api/"

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("x-api-key", "reqres-free-v1")
            .build()
        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()


    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideEventService(
        retrofit: Retrofit
    ): EventService {
        return retrofit.create(EventService::class.java)
    }

    @Provides
    @Singleton
    fun provideSignUpService(
        retrofit: Retrofit
    ): SignUpService {
        return retrofit.create(SignUpService::class.java)
    }
    @Provides
    @Singleton
    fun provideSignInService(
        retrofit: Retrofit
    ): SignInService {
        return retrofit.create(SignInService::class.java)
    }

}