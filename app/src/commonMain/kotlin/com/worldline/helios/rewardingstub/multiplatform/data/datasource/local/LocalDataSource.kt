package com.worldline.helios.rewardingstub.multiplatform.data.datasource.local

import com.worldline.helios.rewardingstub.multiplatform.domain.model.Either
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Error
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Forecast
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Success

interface LocalDataSource {
    suspend fun saveForecast(forecast: Forecast): Either<Error, Success>
}
