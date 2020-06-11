package com.worldline.helios.rewardingstub.multiplatform.data.datasource.remote

import com.worldline.helios.rewardingstub.multiplatform.domain.model.Either
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Error
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Forecast

interface RemoteDataSource {
    suspend fun getForecast(city: String): Either<Error, Forecast>
}
