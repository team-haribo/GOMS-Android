package com.example.gsm_goms.di.module

import com.example.gsm_goms.data.source.signin.SignInDataSource
import com.example.gsm_goms.data.source.signin.SignInDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideSignInDataSource(
        signInDataSourceImpl: SignInDataSourceImpl
    ): SignInDataSource
}