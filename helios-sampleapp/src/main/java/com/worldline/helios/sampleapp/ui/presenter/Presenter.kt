package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.domain.model.Either
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.worldline.helios.sampleapp.ui.error.ErrorHandler
import com.worldline.helios.sampleapp.ui.executor.Executor
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

    abstract fun detach()

    protected suspend fun <T> execute(f: suspend () -> Either<Error, T>): Either<Error, T> =
        withContext(executor.bg) { f() }

    protected suspend fun <C> getResult(f: suspend () -> C) =
        withContext(executor.bg) { f() }
}

interface View {
    fun showProgress()
    fun hideProgress()
    fun showRetry(error: String, f: () -> Unit)
    fun showError(error: String)
}
