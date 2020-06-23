package com.wordline.helios.rewarding.sdk.data.datasource.remote

import com.wordline.helios.rewarding.sdk.data.datasource.local.LocalDataSource
import com.wordline.helios.rewarding.sdk.domain.model.Activity
import com.wordline.helios.rewarding.sdk.domain.model.Either
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.wordline.helios.rewarding.sdk.domain.model.Success
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.http.takeFrom

class CommonRemoteDataSource(localDataSource: LocalDataSource) : RemoteDataSource {

    companion object {
        const val END_POINT_HELIOS = "https://devel3.tempos21.com"
        private const val TOKEN_HEADER = "Authorization"
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

        install(TokenFeature) {
            tokenHeaderName = TOKEN_HEADER
            tokenProvider = localDataSource
        }
    }

    override suspend fun registerUser(
        userID: String,
        context: String
    ): Either<Error, RegisterDataResponse> = execute {
        client.post<RegisterDataResponseDto> {
            call("/hrm-api/auth/networkUser/register")
            val json = io.ktor.client.features.json.defaultSerializer()
            body = json.write(RegisterData(userID = userID, context = context))
        }.toModel()
    }

    override suspend fun registerActivity(action: String, date: String): Either<Error, Success> =
        execute {
            client.post<String> {
                call("/hrm-api/activities/record")
                val json = io.ktor.client.features.json.defaultSerializer()
                body = json.write(listOf(Activity(action = action, date = date)))
            }.toSuccess()
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
            takeFrom(END_POINT_HELIOS)
            encodedPath = "$path"
        }
    }

    private fun String.toSuccess(): Success {
        return Success
    }
}
