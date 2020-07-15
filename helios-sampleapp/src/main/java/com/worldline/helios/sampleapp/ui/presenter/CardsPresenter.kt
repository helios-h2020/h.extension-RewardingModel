package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.GetCardsCallback
import com.wordline.helios.rewarding.sdk.RedeemCardCallback
import com.wordline.helios.rewarding.sdk.RewardingSdk
import com.wordline.helios.rewarding.sdk.domain.model.Card
import com.worldline.helios.sampleapp.ui.error.ErrorHandler
import com.worldline.helios.sampleapp.ui.executor.Executor

class CardsPresenter(
    private val rewardingSdk: RewardingSdk,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: CardsView
) : Presenter<CardsView>(errorHandler, executor, view) {

    override fun attach() {
        getCards()
    }

    override fun detach() {
        rewardingSdk.cancel()
    }

    //If the getCards works It'll show the list of cards in the view.
    fun getCards() {
        rewardingSdk.getCards(object : GetCardsCallback {
            override fun onError() {
                view.showError("error")
            }

            override fun onSuccess(cards: List<Card>) {
                view.showList(cards)
            }

        })
    }

    //If the redeemCard works It'll update the list of cards calling again the getCards.
    fun redeemCard(cardId: String) {
        rewardingSdk.redeemCard(cardId, object: RedeemCardCallback {
            override fun onError() {
                view.showError("error")
            }

            override fun onSuccess() {
                getCards()
            }

        })
    }
}

interface CardsView : View {
    fun showSuccess()
    fun showList(cards: List<Card>)
}
