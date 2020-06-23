package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.data.repository.Repository
import com.worldline.helios.sampleapp.ui.error.ErrorHandler
import com.worldline.helios.sampleapp.ui.executor.Executor
import kotlinx.coroutines.launch

class HomePresenter(
    private val repository: Repository,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: HomeView
) : Presenter<HomeView>(errorHandler, executor, view) {

    override fun attach() {

    }

    fun getToken() {
        scope.launch {
            execute { repository.getToken() }.fold(
                error = { onRetry(it) { getToken() } },
                success = { view.showToken(it) }
            )
        }
    }

    fun removeToken() {
        scope.launch {
            execute { repository.removeToken() }.fold(
                error = { onRetry(it) { removeToken() } },
                success = { }
            )

        }
    }

}

interface HomeView : View {
    fun showToken(token: String)
}
