package com.goms.data.mapper

import com.goms.data.model.outing.OutingCountResponse
import com.goms.domain.data.outing.OutingCountResponseData

object OutingMapper {
    fun outingCountResponseToData(outingCountResponse: OutingCountResponse): OutingCountResponseData {
        return OutingCountResponseData(
            outingCount = outingCountResponse.outingCount
        )
    }
}