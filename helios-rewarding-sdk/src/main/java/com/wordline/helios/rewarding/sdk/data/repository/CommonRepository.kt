package com.wordline.helios.rewarding.sdk.data.repository

import com.wordline.helios.rewarding.sdk.data.datasource.local.LocalDataSource
import com.wordline.helios.rewarding.sdk.data.datasource.remote.RegisterDataResponse
import com.wordline.helios.rewarding.sdk.data.datasource.remote.RemoteDataSource
import com.wordline.helios.rewarding.sdk.domain.model.*

class CommonRepository(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : Repository {

    //It registers an user (userID + context) and saves the token that returns the response.
    override suspend fun registerUser(
        userID: String,
        heliosContext: String
    ): Either<Error, RegisterDataResponse> =
        remote.registerUser(userID, heliosContext)
            .flatMap { registerDataResponse ->
                local.saveToken(registerDataResponse.success)
                    .flatMap { registerDataResponse }

            }

    //It registers a list of activities (actions + date + token).
    override suspend fun registerActivity(activities: List<Activity>): Either<Error, Success> =
        remote.registerActivity(activities)

    override suspend fun getCards(): Either<Error, List<Card>> =
        remote.getCards()

    //It redeems a card owned by the token.
    override suspend fun redeemCard(cardId: String): Either<Error, Success> =
        remote.redeemCard(cardId)


    override suspend fun getToken(): Either<Error, String> {
        return try {
            local.getToken()
        } catch (e: Exception) {
            Either.Left(Error.IO(e.message ?: ""))
        }
    }

    override suspend fun removeToken(): Either<Error, Success> {
        return local.removeToken()
    }
}
