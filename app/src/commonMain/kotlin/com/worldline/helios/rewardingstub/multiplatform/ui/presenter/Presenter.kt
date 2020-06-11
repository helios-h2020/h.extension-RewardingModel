package com.worldline.helios.rewardingstub.multiplatform.ui.presenter

import com.worldline.helios.rewardingstub.multiplatform.domain.model.Either
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Error
import com.worldline.helios.rewardingstub.multiplatform.ui.error.ErrorHandler
import com.worldline.helios.rewardingstub.multiplatform.ui.executor.Executor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext

/**
 * Presenter
 */
abstract class Presenter<out V : View>(
    protected val errorHandler: ErrorHandler,
    private val executor: Executor,
    val view: V
) {

    private val job = SupervisorJob()

    protected val scope = CoroutineScope(job + executor.main)

    protected fun onRetry(error: Error, retry: () -> Unit): Unit =
        view.showRetry(errorHandler.convert(error)) { retry() }

    protected fun onError(error: Error): Unit = view.showError(errorHandler.convert(error))

    abstract fun attach()

    fun detach() = job.cancel()

    protected suspend fun <T> execute(f: suspend () -> Either<Error, T>): Either<Error, T> =
        withContext(executor.bg) { f() }
}

interface View {
    fun showProgress()
    fun hideProgress()
    fun showRetry(error: String, f: () -> Unit)
    fun showError(error: String)
}
