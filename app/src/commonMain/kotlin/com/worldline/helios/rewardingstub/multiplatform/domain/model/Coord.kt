package com.worldline.helios.rewardingstub.multiplatform.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    val lat: Double,
    val lon: Double
)
