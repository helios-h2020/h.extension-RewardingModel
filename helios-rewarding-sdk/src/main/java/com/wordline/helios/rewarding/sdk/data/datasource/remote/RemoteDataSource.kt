package com.wordline.helios.rewarding.sdk.data.datasource.remote

import com.wordline.helios.rewarding.sdk.domain.model.Either
import com.wordline.helios.rewarding.sdk.domain.model.Error

interface RemoteDataSource {
    suspend fun registerUser(userID: String, context: String): Either<Error, RegisterDataResponse>
}
