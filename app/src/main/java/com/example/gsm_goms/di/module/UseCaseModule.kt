package com.example.gsm_goms.di.module

import com.example.gsm_goms.domain.repository.SignInRepository
import com.example.gsm_goms.domain.usecase.SignInUseCase
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
    fun provideSignInUseCase(signInRepository: SignInRepository) = SignInUseCase(signInRepository)
}