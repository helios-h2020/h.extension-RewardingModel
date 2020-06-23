package com.wordline.helios.rewarding.sdk.data.datasource.local

import com.russhwolf.settings.Settings
import com.wordline.helios.rewarding.sdk.data.datasource.remote.RegisterDataResponse
import com.wordline.helios.rewarding.sdk.domain.model.Either
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.wordline.helios.rewarding.sdk.domain.model.Success
import kotlinx.serialization.json.Json

class CommonLocalDataSource(private val settings: Settings) : LocalDataSource {

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }

    override suspend fun getToken(): Either<Error, String> {
        return try {
            Either.Right(settings.getString(ACCESS_TOKEN))
        } catch (e: Exception) {
            Either.Left(Error.IO(e.message ?: ""))
        }
    }

    override suspend fun saveToken(registerDataResponse: RegisterDataResponse): Either<Error, Success> {
        return try {
            settings.putString(ACCESS_TOKEN, registerDataResponse.token)
            Either.Right(Success)
        } catch (e: Exception) {
            Either.Left(Error.TokenNotSaved)
        }
    }

    override suspend fun removeToken(): Either<Error, Success> {
        return try {
            settings.remove(ACCESS_TOKEN)
            Either.Right(Success)
        } catch (e: Exception) {
            Either.Left(Error.TokenNotRemoved)
        }
    }
}
