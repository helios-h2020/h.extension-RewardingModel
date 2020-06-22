package com.worldline.helios.rewardingstub.multiplatform.domain.model

import io.ktor.util.date.GMTDate
import kotlinx.serialization.Serializable

@Serializable
data class Activity (
    val action: String,
    val date: String
)
