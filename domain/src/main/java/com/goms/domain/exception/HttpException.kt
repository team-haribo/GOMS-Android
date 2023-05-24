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
 * 토큰 이슈
 */
class FailAccessTokenException(
    override val message: String?
): RuntimeException()

/**
 * 나가는 학생이 외출 금지일 때
 */
class UserIsBlackListException(
    override val message: String?
): RuntimeException()

/**
 * 올바르지 않은 qr code
 */
class QrCodeExpiredException(
    override val message: String?
): RuntimeException()

/**
 * 서버 에러
 * 500 에러에서 사용
 */
class ServerException(
    override val message: String?
): RuntimeException()