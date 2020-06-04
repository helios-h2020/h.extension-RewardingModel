package com.worldline.multiplatform.data.repository

import com.worldline.multiplatform.data.datasource.local.LocalDataSource
import com.worldline.multiplatform.data.datasource.remote.RemoteDataSource
import com.worldline.multiplatform.domain.model.Either
import com.worldline.multiplatform.domain.model.Error
import com.worldline.multiplatform.domain.model.Forecast

class CommonRepository(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : Repository {

    override suspend fun getForecast(city: String): Either<Error, Forecast> =
        remote.getForecast(city)
            .flatMap { remoteForecast ->
                local.saveForecast(remoteForecast.success)
                    .flatMap { remoteForecast }
            }
}
