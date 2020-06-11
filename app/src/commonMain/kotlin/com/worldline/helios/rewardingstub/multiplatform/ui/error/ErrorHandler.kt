package com.worldline.helios.rewardingstub.multiplatform.ui.error

import com.worldline.helios.rewardingstub.multiplatform.domain.model.Error

expect class ErrorHandler {
    fun convert(error: Error): String
}
