package com.worldline.helios.rewardingstub.multiplatform.ui.presenter

import com.worldline.helios.rewardingstub.multiplatform.data.repository.Repository
import com.worldline.helios.rewardingstub.multiplatform.ui.error.ErrorHandler
import com.worldline.helios.rewardingstub.multiplatform.ui.executor.Executor
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
