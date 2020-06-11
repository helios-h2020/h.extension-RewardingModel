package com.worldline.helios.rewardingstub.multiplatform.data.repository

import com.worldline.helios.rewardingstub.multiplatform.domain.model.Either
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Error
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Forecast

interface Repository {
    suspend fun getForecast(city: String): Either<Error, Forecast>
}
