package com.worldline.helios.rewardingstub.multiplatform.data.datasource.local

import com.russhwolf.settings.Settings
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Either
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Error
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Forecast
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Success
import kotlinx.serialization.json.Json

class CommonLocalDataSource(private val settings: Settings) : LocalDataSource {

    companion object {
        private const val FORECAST_KEY = "FORECAST_KEY"
    }

    override suspend fun saveForecast(forecast: Forecast): Either<Error, Success> {
        return try {
            settings.putString(FORECAST_KEY, Json.stringify(Forecast.serializer(), forecast))
            Either.Right(Success)
        } catch (e: Exception) {
            Either.Left(Error.IO(e.message ?: ""))
        }
    }
}
