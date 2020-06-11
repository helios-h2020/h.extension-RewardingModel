package com.worldline.helios.rewardingstub.multiplatform.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val directionInDegrees: Int,
    val speed: Double
)
