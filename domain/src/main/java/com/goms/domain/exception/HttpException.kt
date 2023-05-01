package com.goms.domain.exception

/**
 * 요청값을 입력하지 않은 경우
 */
class NotRequestParamException(
    override val message: String?
): RuntimeException()

/**
 * 서버 에러
 * 500 에러에서 사용
 */
class ServerException(
    override val message: String?
): RuntimeException()

/**
 * 그 밖의 에러
 */
class OtherException(
    val code: Int,
    override val message: String?
): RuntimeException()