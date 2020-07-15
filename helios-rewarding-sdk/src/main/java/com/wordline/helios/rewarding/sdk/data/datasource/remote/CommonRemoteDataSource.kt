package com.wordline.helios.rewarding.sdk.data.datasource.remote

import com.wordline.helios.rewarding.sdk.data.datasource.local.LocalDataSource
import com.wordline.helios.rewarding.sdk.domain.model.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.http.takeFrom

class CommonRemoteDataSource(localDataSource: LocalDataSource) : RemoteDataSource {

    companion object {
        const val END_POINT_HELIOS = "https://devel3.tempos21.com"
        private const val TOKEN_HEADER = "Authorization"
    }

    private val client = HttpClient(Android) {

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

        install(JsonFeature) {
            serializer = GsonSerializer()
        }

        //Install the token in the API's calls
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

    override suspend fun recordRewardableActivity(rewardableActivities: List<RewardableActivity>): Either<Error, Success> =
        execute {
            client.post<String> {
                call("/hrm-api/activities/record")
                val json = io.ktor.client.features.json.defaultSerializer()
                body = json.write(rewardableActivities)
            }.toSuccess()
        }

    override suspend fun getCards(): Either<Error, List<Card>> = execute {
        client.get<List<Card>> {
            call("/hrm-api/cards")
        }
    }

    override suspend fun redeemCard(cardId: String): Either<Error, Success> = execute {
        client.delete<String> {
            call("/hrm-api/cards/redeemedCard?cardID=$cardId")
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
            encodedPath = path
        }
    }

    private fun String.toSuccess(): Success {
        return Success
    }
}
