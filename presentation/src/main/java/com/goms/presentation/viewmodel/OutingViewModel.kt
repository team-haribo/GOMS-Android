package com.goms.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.goms.domain.data.outing.OutingCountResponseData
import com.goms.domain.data.user.UserResponseData
import com.goms.domain.exception.*
import com.goms.domain.usecase.admin.DeleteOutingUseCase
import com.goms.domain.usecase.outing.OutingCountUseCase
import com.goms.domain.usecase.outing.OutingListUseCase
import com.goms.domain.usecase.outing.OutingStudentSearchUseCase
import com.goms.domain.usecase.outing.OutingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import retrofit2.HttpException
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class OutingViewModel @Inject constructor(
    private val outingUseCase: OutingUseCase,
    private val outingListUseCase: OutingListUseCase,
    private val outingCountUseCase: OutingCountUseCase,
    private val outingStudentSearchUseCase: OutingStudentSearchUseCase,
    private val deleteOutingUseCase: DeleteOutingUseCase
) : ViewModel() {
    private val _isOuting: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isOuting: StateFlow<Boolean?> = _isOuting

    private val _outingList: MutableStateFlow<List<UserResponseData>?> = MutableStateFlow(null)
    val outingList: StateFlow<List<UserResponseData>?> = _outingList

    private val _outingCount: MutableStateFlow<OutingCountResponseData?> = MutableStateFlow(null)
    val outingCount: StateFlow<OutingCountResponseData?> = _outingCount

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun outingLogic(outingUUID: UUID) {
        outingUseCase(outingUUID).collect {
            if (it.isSuccessful)
                _isOuting.value = true
            else {
                val errorBody = it.errorBody()?.string()
                val jsonObject = JSONObject(errorBody!!)

                when (it.code()) {
                    400 -> {
                        when (jsonObject.getString("message")) {
                            "검증되지 않은 외출 식별자 입니다." -> throw QrCodeExpiredException("올바르지 않은 Qr Code입니다.")
                            "블랙리스트인 학생은 외출을 할 수 없습니다." -> throw UserIsBlackListException("외출 금지인 사용자입니다.")
                        }
                    }
                    401 -> throw FailAccessTokenException("access token이 유효하지 않습니다")
                    500 -> throw ServerException("서버 에러")
                }
            }
        }
    }

    suspend fun outingListLogic() {
        outingListUseCase().onStart {
            _isLoading.value = true
        }.onCompletion {
            _isLoading.value = false
        }.catch {
            if (it is HttpException) {
                when (it.code()) {
                    401 -> throw FailAccessTokenException("access token이 유효하지 않습니다")
                    500 -> throw ServerException("서버 에러")
                }
            } else throw OtherException(it.message)
        }.collect {
            _outingList.value = it
        }
    }

    suspend fun outingCount() {
        outingCountUseCase().onStart {
            _isLoading.value = true
        }.onCompletion {
            _isLoading.value = false
        }.catch {
            if (it is HttpException) {
                when (it.code()) {
                    401 -> throw FailAccessTokenException("access token이 유효하지 않습니다")
                }
            } else throw OtherException(it.message)
        }.collect {
            _outingCount.value = it
        }
    }


    suspend fun searchOutingStudent(name: String){
        outingStudentSearchUseCase(name).catch {
          if (it is HttpException){
                when (it.code()){
                    401 -> throw FailAccessTokenException("access token이 유효하지 않습니다")
                }
            }else throw OtherException(it.message)
        }.collect{
            _outingList.value = it
        }
    }

    suspend fun deleteOuting(accountIdx: UUID) {
        deleteOutingUseCase(accountIdx).onStart {
            _isLoading.value = true
        }.onCompletion {
            _isLoading.value = false
        }.catch {
            if (it is HttpException) {
                when (it.code()) {
                    403 -> throw NotCouncilException("학생회 계정이 아닙니다.")
                    500 -> throw ServerException("서버 에러")
                }
            } else throw OtherException(it.message)
        }.collect {
            when (it.code()) {
                403 -> throw NotCouncilException("학생회 계정이 아닙니다.")
                500 -> throw ServerException("서버 에러")
            }
        }
    }
    
}