package com.wordline.helios.rewarding.sdk.data.repository

import com.wordline.helios.rewarding.sdk.data.datasource.remote.RegisterDataResponse
import com.wordline.helios.rewarding.sdk.domain.model.*

interface Repository {
    suspend fun registerUser(
        userID: String,
        heliosContext: String
    ): Either<Error, RegisterDataResponse>
    
    suspend fun recordRewardableActivity(rewardableActivities: List<RewardableActivity>): Either<Error, Success>
    suspend fun getCards(): Either<Error, List<Card>>
    suspend fun redeemCard(cardId: String): Either<Error, Success>
    suspend fun removeToken(): Either<Error, Success>
    suspend fun getToken(): Either<Error, String>
}