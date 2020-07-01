package com.wordline.helios.rewarding.sdk.data.datasource.remote

import com.wordline.helios.rewarding.sdk.domain.model.*

interface RemoteDataSource {
    suspend fun registerUser(userID: String, context: String): Either<Error, RegisterDataResponse>
    suspend fun registerActivity(activities: List<Activity>): Either<Error, Success>
    suspend fun getCards(): Either<Error, List<Card>>
    suspend fun redeemCard(cardId: String): Either<Error, Success>
}
