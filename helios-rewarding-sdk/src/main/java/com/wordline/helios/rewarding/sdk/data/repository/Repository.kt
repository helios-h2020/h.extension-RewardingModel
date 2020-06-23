package com.wordline.helios.rewarding.sdk.data.repository

import com.wordline.helios.rewarding.sdk.data.datasource.remote.RegisterDataResponse
import com.wordline.helios.rewarding.sdk.domain.model.Either
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.wordline.helios.rewarding.sdk.domain.model.Success

interface Repository {
    suspend fun registerUser(
        userID: String,
        heliosContext: String
    ): Either<Error, RegisterDataResponse>

    suspend fun registerActivity(action: String, date: String): Either<Error, Success>
    suspend fun removeToken(): Either<Error, Success>
    suspend fun getToken(): Either<Error, String>
}