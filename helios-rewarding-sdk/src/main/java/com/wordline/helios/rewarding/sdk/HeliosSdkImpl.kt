package com.wordline.helios.rewarding.sdk

import android.content.Context
import com.russhwolf.settings.AndroidSettings
import com.wordline.helios.rewarding.sdk.data.datasource.local.CommonLocalDataSource
import com.wordline.helios.rewarding.sdk.data.datasource.remote.CommonRemoteDataSource
import com.wordline.helios.rewarding.sdk.data.datasource.remote.RegisterDataResponse
import com.wordline.helios.rewarding.sdk.data.repository.CommonRepository
import com.wordline.helios.rewarding.sdk.domain.model.Either
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.wordline.helios.rewarding.sdk.domain.model.Success
import com.wordline.helios.rewarding.sdk.executor.Executor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface HeliosSdk {
    fun registerUser(
        userID: String,
        heliosContext: String,
        registerUserResult: HeliosSdkImpl.RegisterUserResult
    )

    fun removeToken(result: HeliosSdkImpl.RemoveTokenResult)
    fun getToken(result: HeliosSdkImpl.GetTokenResult)
}

class HeliosSdkImpl : HeliosSdk {

    interface RegisterUserResult {
        fun onError()
        fun onResult(registerDataResponse: RegisterDataResponse)
    }

    interface GetTokenResult {
        fun onError()
        fun onResult(token: String)
    }

    interface RemoveTokenResult {
        fun onError()
        fun onResult(success: Success)
    }

    companion object {

        private lateinit var commonRepository: CommonRepository

        private lateinit var executor: Executor

        private lateinit var scope: CoroutineScope

        fun init(context: Context) {
            executor = Executor()
            scope = CoroutineScope(SupervisorJob() + executor.main)
            val commonLocalDataSource = CommonLocalDataSource(
                AndroidSettings(
                    context.getSharedPreferences(
                        "bd",
                        Context.MODE_PRIVATE
                    )
                )
            )
            commonRepository = CommonRepository(
                CommonRemoteDataSource(localDataSource = commonLocalDataSource),
                commonLocalDataSource
            )
        }
    }

    override fun registerUser(
        userID: String,
        heliosContext: String,
        registerUserResult: RegisterUserResult
    ) {
        scope.launch {
            execute { commonRepository.registerUser(userID, heliosContext) }
                .fold(
                    error = { registerUserResult.onError() },
                    success = { registerUserResult.onResult(it) }
                )
        }
    }


    override fun getToken(result: GetTokenResult) {
        scope.launch {
            execute { commonRepository.getToken() }
                .fold(
                    error = { result.onError() },
                    success = { result.onResult(it) }
                )
        }
    }

    override fun removeToken(result: RemoveTokenResult) {
        scope.launch {
            execute { commonRepository.removeToken() }
                .fold(
                    error = { result.onError() },
                    success = { result.onResult(it) }
                )
        }
    }

    private suspend fun <T> execute(f: suspend () -> Either<Error, T>): Either<Error, T> =
        withContext(executor.bg) { f() }
}