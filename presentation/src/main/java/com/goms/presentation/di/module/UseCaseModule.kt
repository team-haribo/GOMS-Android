package com.goms.presentation.di.module

import com.goms.domain.repository.AuthRepository
import com.goms.domain.repository.ProfileRepository
import com.goms.domain.usecase.auth.CheckLoginUseCase
import com.goms.domain.usecase.auth.SetTokenUseCase
import com.goms.domain.usecase.auth.SignInUseCase
import com.goms.domain.usecase.profile.ProfileUseCase
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
    fun provideSignInUseCase(authRepository: AuthRepository) =
        SignInUseCase(authRepository)

    @Provides
    @Singleton
    fun provideCheckLoginUseCase(authRepository: AuthRepository) =
        CheckLoginUseCase(authRepository)

    @Provides
    @Singleton
    fun provideSaveTokenUseCase(authRepository: AuthRepository) =
        SetTokenUseCase(authRepository)

    @Provides
    @Singleton
    fun provideProfileUseCase(
        profileRepository: ProfileRepository,
        authRepository: AuthRepository
    ) = ProfileUseCase(profileRepository, authRepository)
}