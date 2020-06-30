package com.wordline.helios.rewarding.sdk.data.datasource.local

import com.wordline.helios.rewarding.sdk.data.datasource.remote.RegisterDataResponse
import com.wordline.helios.rewarding.sdk.domain.model.Either
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.wordline.helios.rewarding.sdk.domain.model.Success

interface LocalDataSource {
    suspend fun saveToken(registerDataResponse: RegisterDataResponse): Either<Error, Success>

    suspend fun getToken(): Either<Error, String>

    suspend fun removeToken(): Either<Error, Success>
}
