package com.worldline.multiplatform.data.datasource.remote

import com.worldline.multiplatform.domain.model.Either
import com.worldline.multiplatform.domain.model.Error
import com.worldline.multiplatform.domain.model.Forecast

interface RemoteDataSource {
    suspend fun getForecast(city: String): Either<Error, Forecast>
}
