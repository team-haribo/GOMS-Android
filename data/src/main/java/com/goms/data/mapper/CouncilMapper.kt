package com.goms.data.mapper

import com.goms.data.model.council.ModifyRoleRequest
import com.goms.domain.data.council.ModifyRoleRequestData

object CouncilMapper {
    fun modifyRoleRequestToDomain(modifyRoleRequestData: ModifyRoleRequestData): ModifyRoleRequest {
        return ModifyRoleRequest(
            accountIdx = modifyRoleRequestData.accountIdx,
            authority = modifyRoleRequestData.authority
        )
    }
}