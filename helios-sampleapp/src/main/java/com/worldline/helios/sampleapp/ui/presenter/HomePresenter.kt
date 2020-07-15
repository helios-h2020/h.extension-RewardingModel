package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.GetTokenCallback
import com.wordline.helios.rewarding.sdk.RemoveTokenCallback
import com.wordline.helios.rewarding.sdk.RewardingSdk
import com.worldline.helios.sampleapp.ui.error.ErrorHandler
import com.worldline.helios.sampleapp.ui.executor.Executor

class HomePresenter(
    private val rewardingSdk: RewardingSdk,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: HomeView
) : Presenter<HomeView>(errorHandler, executor, view) {

    override fun attach() {

    }

    override fun detach() {
        rewardingSdk.cancel()
    }

    fun getToken() {
        rewardingSdk.getToken(object : GetTokenCallback {
            override fun onError() {
                view.showError("error")
            }

            override fun onSuccess(token: String) {
                view.showToken(token)
            }
        })
    }

    fun removeToken() {
        rewardingSdk.removeToken(object : RemoveTokenCallback {
            override fun onError() {
                view.showError("error")
            }
        })
    }

}

interface HomeView : View {
    fun showToken(token: String)
}
