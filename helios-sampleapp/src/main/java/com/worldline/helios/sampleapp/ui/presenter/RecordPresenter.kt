package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.data.repository.Repository
import com.worldline.helios.sampleapp.ui.error.ErrorHandler
import com.worldline.helios.sampleapp.ui.executor.Executor
import kotlinx.coroutines.launch

class RecordPresenter(
    private val repository: Repository,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: RecordView
) : Presenter<RecordView>(errorHandler, executor, view) {

    override fun attach() {

    }

    fun registerActivity(action: String, date: String) {
        scope.launch {
            execute { repository.registerActivity(action, date) }.fold(
                error = { println("error") },
                success = { view.showSuccess() }
            )
        }
    }
}

interface RecordView : View {
    fun showSuccess()
}
