package com.wordline.helios.rewarding.sdk.data.datasource.remote

import com.wordline.helios.rewarding.sdk.domain.model.*

interface RemoteDataSource {
    val endPoint: String

    suspend fun registerUser(userID: String, context: String): Either<Error, RegisterDataResponse>
    suspend fun recordRewardableActivity(rewardableActivities: List<RewardableActivity>): Either<Error, Success>
    suspend fun getCards(): Either<Error, List<Card>>
    suspend fun redeemCard(cardId: String): Either<Error, Success>
}
