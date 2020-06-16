package com.worldline.helios.rewardingstub.multiplatform.ui.presenter

import com.worldline.helios.rewardingstub.multiplatform.data.repository.Repository
import com.worldline.helios.rewardingstub.multiplatform.ui.error.ErrorHandler
import com.worldline.helios.rewardingstub.multiplatform.ui.executor.Executor
import kotlinx.coroutines.launch

class RecordPresenter(
    private val repository: Repository,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: RecordView
) : Presenter<RecordView>(errorHandler, executor, view) {

    override fun attach() {

    }
}

interface RecordView : View {

}
