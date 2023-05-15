package com.goms.presentation.viewmodel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.ViewModel
import com.goms.domain.data.outing.OutingCountResponseData
import com.goms.domain.data.user.UserResponseData
import com.goms.domain.exception.FailAccessTokenException
import com.goms.domain.exception.OtherException
import com.goms.domain.exception.ServerException
import com.goms.domain.exception.UserIsBlackListException
import com.goms.domain.usecase.outing.OutingCountUseCase
import com.goms.domain.usecase.outing.OutingListUseCase
import com.goms.domain.usecase.outing.OutingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class OutingViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val outingUseCase: OutingUseCase,
    private val outingListUseCase: OutingListUseCase,
    private val outingCountUseCase: OutingCountUseCase
): ViewModel() {
    private val _isOuting: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isOuting: StateFlow<Boolean?> = _isOuting

    private val _outingList: MutableStateFlow<List<UserResponseData>?> = MutableStateFlow(null)
    val outingList: StateFlow<List<UserResponseData>?> = _outingList

    private val _outingCount: MutableStateFlow<OutingCountResponseData?> = MutableStateFlow(null)
    val outingCount: StateFlow<OutingCountResponseData?> = _outingCount

    suspend fun outingLogic(outingUUID: UUID) {
        outingUseCase(outingUUID).onFailure {
            _isOuting.value = false
            if (it is HttpException) {
                when(it.code()) {
                    400 -> throw UserIsBlackListException("외출 금지인 사용자입니다.")
                    401 -> throw FailAccessTokenException("access token이 유효하지 않습니다")
                    500 -> throw ServerException("서버 에러")
                }
            } else throw OtherException(it.message)
        }.onSuccess {
            _isOuting.value = true
        }
    }

    suspend fun outingListLogic() {
        outingListUseCase().catch {
            if (it is HttpException) {
                when(it.code()) {
                    401 -> Log.d("TAG", "outingListLogic: 토큰 에러")
                    404 ->
                        Log.d("TAG", "outingListLogic 404: $it")
                    500 -> Log.d("TAG", "outingListLogic: 서버 에러")
                }
            } else Log.d("TAG", "outingListLogic: $it")
        }.collect {
            manageOutingSharedPreference()
            _outingList.value = it
        }
    }

    suspend fun outingCount() {
        outingCountUseCase().catch {
            Log.d("TAG", "outingCount: $it")
        }.collect {
            _outingCount.value = it
        }
    }

    // 현재 외출 중인지 상태를 체크한다.
    private val sharedPreferences = context.getSharedPreferences("userOuting", MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    private fun manageOutingSharedPreference() {
        if (!sharedPreferences.getBoolean("userOuting", false))
            editor.putBoolean("userOuting", true)
        else editor.putBoolean("userOuting", false)
    }
}