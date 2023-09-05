package com.goms.domain.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NotificationRepository {
    suspend fun setNotification(deviceToken: String): Flow<Response<Unit>>
}