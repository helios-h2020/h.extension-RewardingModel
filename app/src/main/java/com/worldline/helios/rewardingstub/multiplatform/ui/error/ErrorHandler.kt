package com.worldline.helios.rewardingstub.multiplatform.ui.error

import android.content.Context
import com.worldline.helios.rewardingstub.multiplatform.R
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Error

actual class ErrorHandler(private val context: Context) {
    actual fun convert(error: Error): String =
        context.getString(
            when (error) {
                is Error.InvalidCredentials -> R.string.invalid_credentials
                is Error.NoInternet -> R.string.nointernet_error
                else -> R.string.default_error
            }
        )
}
