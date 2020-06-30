package com.worldline.helios.sampleapp.ui.error

import android.content.Context
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.worldline.helios.sampleapp.R

class ErrorHandler(private val context: Context) {
    fun convert(error: Error): String =
        context.getString(
            when (error) {
                is Error.InvalidCredentials -> R.string.invalid_credentials
                is Error.NoInternet -> R.string.nointernet_error
                else -> R.string.default_error
            }
        )
}
