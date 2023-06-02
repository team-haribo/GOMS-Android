package com.goms.domain.exception

import java.io.IOException

class NeedLoginException(): IOException() {
    override val message: String
        get() = "토큰이 만료되었습니다."
}