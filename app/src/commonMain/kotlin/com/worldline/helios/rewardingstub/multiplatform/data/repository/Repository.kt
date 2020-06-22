package com.worldline.helios.rewardingstub.multiplatform.data.repository

import com.worldline.helios.rewardingstub.multiplatform.data.datasource.remote.RegisterDataResponse
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Either
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Error
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Forecast
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Success

interface Repository {
    suspend fun registerUser(userID: String, heliosContext: String): Either<Error, RegisterDataResponse>
    suspend fun registerActivity(action: String, date: String): Either<Error, Success>
    suspend fun removeToken(): Either<Error, Success>
    suspend fun getToken(): Either<Error, String>
}
