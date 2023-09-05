package com.goms.data.repository

import com.goms.data.datasource.notification.NotificationDataSource
import com.goms.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
): NotificationRepository {
    override suspend fun setNotification(deviceToken: String): Flow<Response<Unit>> {
        return flow {
            notificationDataSource.setNotification(deviceToken).collect {
                emit(it)
            }
        }
    }
}