package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.data.repository.Repository
import com.worldline.helios.sampleapp.ui.error.ErrorHandler
import com.worldline.helios.sampleapp.ui.executor.Executor
import kotlinx.coroutines.launch

class CardsPresenter(
    private val repository: Repository,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: CardsView
) : Presenter<CardsView>(errorHandler, executor, view) {

    override fun attach() {

    }

    fun getCards() {
        scope.launch {
            execute { repository.getCards() }.fold(
                error = { println("error") },
                success = { println("success") }
            )
        }
    }

    fun redeemCard(cardId: String) {
        scope.launch {
            execute {
                repository.redeemCard(cardId)
            }
        }
    }
}

interface CardsView : View {

}
