package com.worldline.helios.rewardingstub.multiplatform.data.datasource.remote

import com.worldline.helios.rewardingstub.multiplatform.domain.model.*
import com.worldline.helios.rewardingstub.multiplatform.data.datasource.remote.*

interface RemoteDataSource {
    suspend fun registerUser(userID: String, context: String): Either<Error, RegisterDataResponse>
}
