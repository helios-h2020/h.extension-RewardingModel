package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.data.repository.Repository
import com.wordline.helios.rewarding.sdk.domain.model.Activity
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

    //If the registerActivity call works It'll show a toast with a message.
    fun registerActivity(actions: List<String>, date: String) {
        scope.launch {
            val activities: MutableList<Activity> = mutableListOf<Activity>()
            for (action in actions) {
                activities.add(Activity(action = action, date = date))
            }
            execute { repository.registerActivity(activities) }.fold(
                error = { println("error") },
                success = { view.showSuccess() }
            )
        }
    }
}

interface RecordView : View {
    fun showSuccess()
}
