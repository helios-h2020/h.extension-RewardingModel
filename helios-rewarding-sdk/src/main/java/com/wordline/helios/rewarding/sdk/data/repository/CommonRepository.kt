package com.wordline.helios.rewarding.sdk.data.repository

import com.wordline.helios.rewarding.sdk.data.datasource.local.LocalDataSource
import com.wordline.helios.rewarding.sdk.data.datasource.remote.RegisterDataResponse
import com.wordline.helios.rewarding.sdk.data.datasource.remote.RemoteDataSource
import com.wordline.helios.rewarding.sdk.domain.model.Card
import com.wordline.helios.rewarding.sdk.domain.model.Either
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.wordline.helios.rewarding.sdk.domain.model.Success

class CommonRepository(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : Repository {

    override suspend fun registerUser(
        userID: String,
        heliosContext: String
    ): Either<Error, RegisterDataResponse> =
        remote.registerUser(userID, heliosContext)
            .flatMap { registerDataResponse ->
                local.saveToken(registerDataResponse.success)
                    .flatMap { registerDataResponse }

            }

    override suspend fun registerActivity(action: String, date: String): Either<Error, Success> =
        remote.registerActivity(action, date)

    override suspend fun getCards(): Either<Error, Card> =
        remote.getCards()
    /*.flatMap { remoteCard ->

    }*/

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
