package com.wordline.helios.rewarding.sdk.data.datasource.remote

import kotlinx.serialization.Serializable


@Serializable
data class RegisterDataResponseDto(val token: String) {
    fun toModel() = RegisterDataResponse(token)
}

@Serializable
data class RegisterData(
    val userID: String,
    val context: String
)

@Serializable
data class RegisterDataResponse(
    val token: String
)

@Serializable
data class TokenDataDto(val accessToken: String) {
    fun toModel() = TokenData(accessToken)
}

@Serializable
data class TokenData(
    val accessToken: String
)