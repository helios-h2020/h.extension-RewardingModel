package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.data.repository.Repository
import com.worldline.helios.sampleapp.ui.error.ErrorHandler
import com.worldline.helios.sampleapp.ui.executor.Executor
import kotlinx.coroutines.launch

class AuthPresenter(
    private val repository: Repository,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: AuthView
) : Presenter<AuthView>(errorHandler, executor, view) {

    override fun attach() {

    }

    fun registerUser(userID: String, context: String) {
        scope.launch {
            execute { repository.registerUser(userID, context) }.fold(
                error = { println("error") },
                success = { view.showSuccess() }
            )
        }
    }
}

interface AuthView : View {
    fun showSuccess()
}
