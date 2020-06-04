package com.worldline.multiplatform.ui.error

import com.worldline.multiplatform.domain.model.Error

expect class ErrorHandler {
    fun convert(error: Error): String
}
