package com.goms.presentation.di.module

import com.goms.data.repository.AuthRepositoryImpl
import com.goms.data.repository.CouncilRepositoryImpl
import com.goms.data.repository.LateRepositoryImpl
import com.goms.data.repository.OutingRepositoryImpl
import com.goms.data.repository.ProfileRepositoryImpl
import com.goms.domain.repository.AuthRepository
import com.goms.domain.repository.CouncilRepository
import com.goms.domain.repository.LateRepository
import com.goms.domain.repository.OutingRepository
import com.goms.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindSignInRepository(
        signInDataSourceImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    abstract fun bindOutingRepository(
        outingRepositoryImpl: OutingRepositoryImpl
    ): OutingRepository

    @Binds
    abstract fun bindLateRepository(
        lateRepositoryImpl: LateRepositoryImpl
    ): LateRepository

    @Binds
    abstract fun bindCouncilRepository(
        councilRepositoryImpl: CouncilRepositoryImpl
    ): CouncilRepository
}