package com.goms.domain.usecase.notification

import com.goms.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class SetNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(deviceToken: String): Flow<Response<Unit>> {
        return notificationRepository.setNotification(deviceToken)
    }
}