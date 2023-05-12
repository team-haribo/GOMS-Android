package com.goms.domain.exception

/**
 * 그 밖의 에러
 */
class OtherException(
    override val message: String?
): RuntimeException()