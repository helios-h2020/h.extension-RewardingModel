package com.worldline.helios.rewardingstub.multiplatform.ui.presenter

import com.worldline.helios.rewardingstub.multiplatform.data.repository.Repository
import com.worldline.helios.rewardingstub.multiplatform.ui.error.ErrorHandler
import com.worldline.helios.rewardingstub.multiplatform.ui.executor.Executor
import kotlinx.coroutines.launch

class CardsPresenter(
    private val repository: Repository,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: CardsView
) : Presenter<CardsView>(errorHandler, executor, view) {

    override fun attach() {

    }
}

interface CardsView : View {

}
