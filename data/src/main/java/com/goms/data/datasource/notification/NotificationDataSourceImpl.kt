package com.goms.data.datasource.notification

import com.goms.data.api.NotificationApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val notificationApi: NotificationApi
): NotificationDataSource {
    override suspend fun setNotification(deviceToken: String): Flow<Response<Unit>> {
        return flow {
            emit(notificationApi.setNotification(deviceToken))
        }
    }
}