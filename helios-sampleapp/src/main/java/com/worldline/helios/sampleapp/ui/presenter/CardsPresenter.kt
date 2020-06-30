package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.data.repository.Repository
import com.wordline.helios.rewarding.sdk.domain.model.Card
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
        getCards()
    }

    //If the getCards works It'll show the list of cards in the view.
    fun getCards() {
        scope.launch {
            execute { repository.getCards() }.fold(
                error = { view.showError("Error: Getting the cards.") },
                success = { view.showList(cards = it) }
            )
        }
    }

    //If the redeemCard works It'll update the list of cards calling again the getCards.
    fun redeemCard(cardId: String) {
        scope.launch {
            execute {
                repository.redeemCard(cardId)
            }.fold(
                error = { view.showError("Error: Redeeming the card.")},
                success = { getCards() }
            )
        }
    }
}

interface CardsView : View {
    fun showSuccess()
    fun showList(cards: List<Card>)
}
