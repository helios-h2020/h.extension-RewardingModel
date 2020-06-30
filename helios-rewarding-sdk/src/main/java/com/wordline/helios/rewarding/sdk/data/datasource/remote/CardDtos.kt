package com.wordline.helios.rewarding.sdk.data.datasource.remote

import com.wordline.helios.rewarding.sdk.domain.model.Card
import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val id: String,
    val tokens: String//,
    //val secret: String
) {
    fun toModel() = Card(
        id = id,
        tokens = tokens//,
        //secret = secret
    )
}