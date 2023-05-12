package com.goms.domain.data.council

import java.util.UUID

data class ModifyRoleRequestData(
    val accountIdx: UUID,
    val authority: String
)
