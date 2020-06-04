package com.worldline.multiplatform.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    val country: String,
    val id: Int,
    val message: Double,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)
