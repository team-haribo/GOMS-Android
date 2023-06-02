package com.goms.data.model.outing

import com.google.gson.annotations.SerializedName

data class OutingCountResponse(
    @SerializedName("outingCount") val outingCount: Int
)
