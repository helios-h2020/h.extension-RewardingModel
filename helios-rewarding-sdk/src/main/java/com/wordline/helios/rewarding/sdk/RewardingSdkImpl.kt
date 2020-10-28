package com.wordline.helios.rewarding.sdk

import android.content.Context
import com.russhwolf.settings.AndroidSettings
import com.wordline.helios.rewarding.sdk.data.datasource.local.CommonLocalDataSource
import com.wordline.helios.rewarding.sdk.data.datasource.remote.CommonRemoteDataSource
import com.wordline.helios.rewarding.sdk.data.repository.CommonRepository
import com.wordline.helios.rewarding.sdk.domain.model.Either
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.wordline.helios.rewarding.sdk.domain.model.RewardableActivity
import com.wordline.helios.rewarding.sdk.executor.Executor
import kotlinx.coroutines.*

interface RewardingSdk {
    fun cancel()
    fun registerUser(userID: String, heliosContext: String, callback: RegisterUserCallback)
    fun removeToken(callback: RemoveTokenCallback)
    fun getToken(callback: GetTokenCallback)
    fun recordRewardableActivity(rewardableActivities: List<RewardableActivity>, callback: RecordRewardableActivityCallback)
}

interface RegisterUserCallback {
    fun onError()
    fun onSuccess()
}

interface GetTokenCallback {
    fun onError()
    fun onSuccess(token: String)
}

interface RemoveTokenCallback {
    fun onError()
}

interface RecordRewardableActivityCallback {
    fun onError()
    fun onSuccess()
}

class RewardingSdkImpl : RewardingSdk {

    companion object {

        private lateinit var commonRepository: CommonRepository

        private lateinit var executor: Executor

        private lateinit var job: CompletableJob

        private lateinit var scope: CoroutineScope

        fun init(context: Context, apiUrl: String) {
            executor = Executor()
            job = SupervisorJob()
            scope = CoroutineScope(job + executor.main)
            val commonLocalDataSource = CommonLocalDataSource(
                AndroidSettings(
                    context.getSharedPreferences(
                        "bd",
                        Context.MODE_PRIVATE
                    )
                )
            )
            commonRepository = CommonRepository(
                CommonRemoteDataSource(localDataSource = commonLocalDataSource, endPoint = apiUrl),
                commonLocalDataSource
            )
        }
    }

    override fun cancel() {
        scope.coroutineContext.cancelChildren()
    }

    override fun registerUser(
        userID: String,
        heliosContext: String,
        callback: RegisterUserCallback
    ) {
        scope.launch {
            execute { commonRepository.registerUser(userID, heliosContext) }.fold(
                    error = { callback.onError() },
                    success = { callback.onSuccess() }
                )
        }
    }


    override fun getToken(callback: GetTokenCallback) {
        scope.launch {
            execute { commonRepository.getToken() }.fold(
                    error = { callback.onError() },
                    success = { callback.onSuccess(it) }
                )
        }
    }

    override fun removeToken(callback: RemoveTokenCallback) {
        scope.launch {
            execute { commonRepository.removeToken() }.fold(
                    error = { callback.onError() },
                    success = { }
                )
        }
    }

    override fun recordRewardableActivity(rewardableActivities: List<RewardableActivity>, callback: RecordRewardableActivityCallback) {
        scope.launch {
            execute { commonRepository.recordRewardableActivity(rewardableActivities) }.fold(
                error = { callback.onError() },
                success = { callback.onSuccess() }
            )
        }
    }

    private suspend fun <T> execute(f: suspend () -> Either<Error, T>): Either<Error, T> =
        withContext(executor.bg) { f() }
}