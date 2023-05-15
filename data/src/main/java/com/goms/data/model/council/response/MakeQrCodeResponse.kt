package com.goms.data.model.council.response

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class MakeQrCodeResponse(
    @SerializedName("outingUUID") val outingUUID: UUID
)
