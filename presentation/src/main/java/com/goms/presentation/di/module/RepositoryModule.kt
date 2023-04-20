package com.goms.presentation.di.module

import com.goms.data.repository.SignInRepositoryImpl
import com.goms.data.source.SignInDataSourceImpl
import com.goms.domain.repository.SignInRepository
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