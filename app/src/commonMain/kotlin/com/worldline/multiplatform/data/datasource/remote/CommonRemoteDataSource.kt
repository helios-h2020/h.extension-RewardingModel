package com.worldline.multiplatform.data.datasource.remote

import com.worldline.multiplatform.domain.model.Either
import com.worldline.multiplatform.domain.model.Error
import com.worldline.multiplatform.domain.model.Forecast
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.takeFrom

class CommonRemoteDataSource : RemoteDataSource {

    companion object {
        const val END_POINT = "https://samples.openweathermap.org"
        const val API_KEY = "b6907d289e10d714a6e88b30761fae22"
    }

    private val client = HttpClient {
        // install(Auth) {
        //     basic {
        //         username = AUTH_USER
        //         password = AUTH_PASS
        //     }
        // }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    override suspend fun getForecast(city: String): Either<Error, Forecast> = execute {
        client.get<ForecastDto> {
            call("/data/2.5/weather?q=$city")
        }.toModel()
    }

    private suspend fun <R> execute(f: suspend () -> R): Either<Error, R> =
        try {
            Either.Right(f())
        } catch (e: Throwable) {
            print(e.toString())
            Either.Left(
                when (e) {
                    is ClientRequestException -> when (e.response.status) {
                        HttpStatusCode.Unauthorized -> Error.InvalidCredentials
                        HttpStatusCode.NotFound -> Error.NotFound
                        HttpStatusCode.BadRequest -> Error.NoInternet
                        else -> Error.Default(e.message ?: "")
                    }
                    else -> Error.Default(e.message ?: "")
                }
            )
        }

    private fun HttpRequestBuilder.call(path: String) {
        url {
            takeFrom(END_POINT)
            encodedPath = "$path&appid=$API_KEY"
        }
    }
}
