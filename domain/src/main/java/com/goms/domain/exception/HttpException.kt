package com.goms.domain.exception

/**
 * 요청값을 입력하지 않은 경우
 */
class NotRequestParamException(
    override val message: String?
): RuntimeException()

/**
 * 학생회 계정이 아님
 */
class NotCouncilException(
    override val message: String?
): RuntimeException()

/**
 * 계정을 찾을 수 없음
 */
class UserNotFoundException(
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
    override val message: String?
): RuntimeException()
