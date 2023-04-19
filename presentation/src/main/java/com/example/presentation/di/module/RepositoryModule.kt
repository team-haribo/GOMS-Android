package com.example.presentation.di.module

import com.example.data.repository.SignInRepositoryImpl
import com.example.data.source.SignInDataSourceImpl
import com.example.domain.repository.SignInRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSignInRepository(signInDataSourceImpl: SignInDataSourceImpl): SignInRepository {
        return SignInRepositoryImpl(signInDataSourceImpl)
    }
}