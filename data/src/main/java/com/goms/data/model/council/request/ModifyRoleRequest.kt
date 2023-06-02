package com.goms.data.model.council.request

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ModifyRoleRequest(
    @SerializedName("accountIdx") val accountIdx: UUID,
    @SerializedName("authority") val authority: String
)