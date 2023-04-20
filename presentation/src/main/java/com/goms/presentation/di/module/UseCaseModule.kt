package com.goms.presentation.di.module

import com.goms.domain.repository.SignInRepository
import com.goms.domain.usecase.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSignInUseCase(signInRepository: SignInRepository) =
        SignInUseCase(signInRepository)
}