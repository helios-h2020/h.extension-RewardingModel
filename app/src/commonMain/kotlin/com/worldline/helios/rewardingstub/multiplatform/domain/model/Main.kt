package com.worldline.helios.rewardingstub.multiplatform.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Main(
    val humidity: Int,
    val pressure: Int,
    val temperature: Double,
    val temperatureMax: Double,
    val temperatureMin: Double
)
