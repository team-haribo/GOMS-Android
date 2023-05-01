package com.goms.presentation.di.module

import com.goms.data.repository.AuthRepositoryImpl
import com.goms.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideSignInRepository(
        signInDataSourceImpl: AuthRepositoryImpl
    ): AuthRepository
}