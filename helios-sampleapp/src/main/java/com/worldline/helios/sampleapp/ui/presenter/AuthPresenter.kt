package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.RegisterUserCallback
import com.wordline.helios.rewarding.sdk.RewardingSdk
import com.worldline.helios.sampleapp.ui.error.ErrorHandler
import com.worldline.helios.sampleapp.ui.executor.Executor

class AuthPresenter(
    private val rewardingSdk: RewardingSdk,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: AuthView
) : Presenter<AuthView>(errorHandler, executor, view) {

    override fun attach() {

    }

    override fun detach() {
        rewardingSdk.cancel()
    }

    //If the registerUser call works It'll show a toast with a message.
    fun registerUser(userID: String, context: String) {
        rewardingSdk.registerUser(userID, context, object : RegisterUserCallback {
            override fun onError() {
                view.showError("error")
            }

            override fun onSuccess() {
                view.showSuccess()
            }
        })
    }
}

interface AuthView : View {
    fun showSuccess()
}
