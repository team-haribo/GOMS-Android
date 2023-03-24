package com.example.gsm_goms.di.module

import com.example.gsm_goms.data.repository.SignInRepositoryImpl
import com.example.gsm_goms.data.source.signin.SignInDataSourceImpl
import com.example.gsm_goms.domain.repository.SignInRepository
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