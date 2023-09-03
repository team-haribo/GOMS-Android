package com.goms.data.datasource.notification

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NotificationDataSource {
    suspend fun setNotification(deviceToken: String): Flow<Response<Unit>>
}