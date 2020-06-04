package com.worldline.multiplatform.data.repository

import com.worldline.multiplatform.domain.model.Either
import com.worldline.multiplatform.domain.model.Error
import com.worldline.multiplatform.domain.model.Forecast

interface Repository {
    suspend fun getForecast(city: String): Either<Error, Forecast>
}
