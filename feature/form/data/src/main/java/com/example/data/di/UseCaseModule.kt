package com.example.data.di
//
//import com.example.domain.repository.FormRepository
//import com.example.domain.usecase.form.GetFormUseCase
//import com.example.domain.usecase.validation.EmptyFieldUseCase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//object UseCaseModule {
//    @Provides
//    fun provideGetFormUseCase(
//        repo: FormRepository
//    ): GetFormUseCase = GetFormUseCase(repo)
//
//    @Provides
//    fun provideEmptyFieldUseCase(): EmptyFieldUseCase =
//        EmptyFieldUseCase()
//}