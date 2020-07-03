package com.wordline.helios.rewarding.sdk.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RewardableActivity(
    val action: String,
    val date: String
)