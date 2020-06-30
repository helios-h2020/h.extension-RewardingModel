package com.wordline.helios.rewarding.sdk.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val id: String,
    val tokens: String//,
    //val secret: String
)
