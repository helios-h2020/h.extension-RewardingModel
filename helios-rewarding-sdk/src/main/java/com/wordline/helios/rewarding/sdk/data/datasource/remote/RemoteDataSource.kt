package com.wordline.helios.rewarding.sdk.data.datasource.remote

import com.wordline.helios.rewarding.sdk.domain.model.Either
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.wordline.helios.rewarding.sdk.domain.model.Success

interface RemoteDataSource {
    suspend fun registerUser(userID: String, context: String): Either<Error, RegisterDataResponse>
    suspend fun registerActivity(action: String, date: String): Either<Error, Success>
}
