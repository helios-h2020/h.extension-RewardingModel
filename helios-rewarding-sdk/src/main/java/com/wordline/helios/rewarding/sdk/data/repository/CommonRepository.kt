package com.wordline.helios.rewarding.sdk.data.repository

import com.wordline.helios.rewarding.sdk.data.datasource.local.LocalDataSource
import com.wordline.helios.rewarding.sdk.data.datasource.remote.RegisterDataResponse
import com.wordline.helios.rewarding.sdk.data.datasource.remote.RemoteDataSource
import com.wordline.helios.rewarding.sdk.domain.model.Either
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.wordline.helios.rewarding.sdk.domain.model.Success

class CommonRepository(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : Repository {

    override suspend fun registerUser(userID: String, context: String): Either<Error, RegisterDataResponse> =
        remote.registerUser(userID, context)
            .flatMap {registerDataResponse ->
                local.saveToken(registerDataResponse.success)
                    .flatMap { registerDataResponse }

            }
    override suspend fun getToken(): Either<Error, String> {
        return local.getToken()
    }

    override suspend fun removeToken(): Either<Error, Success> {
        return local.removeToken()
    }
}
