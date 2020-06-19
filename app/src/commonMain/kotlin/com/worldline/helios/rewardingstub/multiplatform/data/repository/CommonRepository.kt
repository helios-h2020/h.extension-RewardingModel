package com.worldline.helios.rewardingstub.multiplatform.data.repository

import com.worldline.helios.rewardingstub.multiplatform.data.datasource.local.LocalDataSource
import com.worldline.helios.rewardingstub.multiplatform.data.datasource.remote.RegisterDataResponse
import com.worldline.helios.rewardingstub.multiplatform.data.datasource.remote.RemoteDataSource
import com.worldline.helios.rewardingstub.multiplatform.domain.model.*

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
