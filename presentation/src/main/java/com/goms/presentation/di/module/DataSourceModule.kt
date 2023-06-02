package com.goms.presentation.di.module

import com.goms.data.datasource.admin.CouncilDataSource
import com.goms.data.datasource.admin.CouncilDataSourceImpl
import com.goms.data.datasource.auth.AuthDataSource
import com.goms.data.datasource.auth.AuthDataSourceImpl
import com.goms.data.datasource.late.LateDataSource
import com.goms.data.datasource.late.LateDataSourceImpl
import com.goms.data.datasource.outing.OutingDataSource
import com.goms.data.datasource.outing.OutingDataSourceImpl
import com.goms.data.datasource.profile.ProfileDataSource
import com.goms.data.datasource.profile.ProfileDataSourceImpl
import com.goms.data.datasource.token.AuthTokenDataSource
import com.goms.data.datasource.token.AuthTokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindSignInDataSource(
        signInDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    abstract fun bindManageTokenDataSource(
        manageTokenDataSourceImpl: AuthTokenDataSourceImpl
    ): AuthTokenDataSource

    @Binds
    abstract fun bindProfileDataSource(
        profileDataSourceImpl: ProfileDataSourceImpl
    ): ProfileDataSource

    @Binds
    abstract fun bindOutingDataSource(
        outingDataSourceImpl: OutingDataSourceImpl
    ): OutingDataSource

    @Binds
    abstract fun bindLateDataSource(
        lateDataSourceImpl: LateDataSourceImpl
    ): LateDataSource

    @Binds
    abstract fun bindCouncilDataSource(
        councilDataSourceImpl: CouncilDataSourceImpl
    ): CouncilDataSource
}