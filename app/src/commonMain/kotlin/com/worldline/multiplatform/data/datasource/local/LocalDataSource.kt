package com.worldline.multiplatform.data.datasource.local

import com.worldline.multiplatform.domain.model.Either
import com.worldline.multiplatform.domain.model.Error
import com.worldline.multiplatform.domain.model.Forecast
import com.worldline.multiplatform.domain.model.Success

interface LocalDataSource {
    suspend fun saveForecast(forecast: Forecast): Either<Error, Success>
}
