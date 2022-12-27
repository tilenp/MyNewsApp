package com.example.domain.hilt

import com.example.domain.usecase.ErrorMessageUseCase
import com.example.domain.usecase.GetArticlesUseCase
import com.example.domain.usecase.impl.ErrorMessageUseCaseImpl
import com.example.domain.usecase.impl.GetArticlesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal abstract class UseCaseModule {

    @Binds
    abstract fun bindErrorMessageUseCase(errorMessageUseCaseImpl: ErrorMessageUseCaseImpl): ErrorMessageUseCase

    @Binds
    abstract fun bindGetArticlesUseCase(getArticlesUseCaseImpl: GetArticlesUseCaseImpl): GetArticlesUseCase
}