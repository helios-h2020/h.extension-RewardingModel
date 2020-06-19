package com.worldline.helios.rewardingstub.multiplatform.data.datasource.remote

import com.worldline.helios.rewardingstub.multiplatform.domain.model.*
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
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.http.takeFrom
import io.ktor.util.date.GMTDate

class CommonRemoteDataSource : RemoteDataSource {

    companion object {
        const val END_POINT_HELIOS = "https://devel3.tempos21.com"
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

    override suspend fun registerUser(userID: String, context: String): Either<Error, RegisterDataResponse> = execute {
        client.post<RegisterDataResponseDto> {
            call("/hrm-api/auth/networkUser/register")
            val json = io.ktor.client.features.json.defaultSerializer()
            body =  json.write(RegisterData(userID = userID, context = context))
        }.toModel()
    }

    override suspend fun registerActivity(action: String, date: String): Either<Error, Success> = execute {
        client.post<Success> {
            call("/hrm-api/activities/record")
            val json = io.ktor.client.features.json.defaultSerializer()
            body =  json.write(Activity(action = action, date = date))
        }
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
