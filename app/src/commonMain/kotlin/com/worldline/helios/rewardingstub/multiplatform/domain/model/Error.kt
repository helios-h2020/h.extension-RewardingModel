package com.worldline.helios.rewardingstub.multiplatform.domain.model

sealed class Error {
    object NoInternet : Error()
    object NotFound : Error()
    object TokenNotSaved : Error()
    object TokenNotRemoved : Error()
    object InvalidCredentials : Error()
    data class IO(val message: String): Error()
    data class Default(val message: String) : Error()
}

object Success
