package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.HeliosSdkImpl
import com.wordline.helios.rewarding.sdk.HeliosSdk
import com.wordline.helios.rewarding.sdk.data.datasource.remote.RegisterDataResponse
import com.wordline.helios.rewarding.sdk.domain.model.Error
import com.wordline.helios.rewarding.sdk.domain.model.Success
import com.worldline.helios.sampleapp.ui.error.ErrorHandler
import com.worldline.helios.sampleapp.ui.executor.Executor

class SdkPresenter(
    private val heliosSdk: HeliosSdk,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: SdkView
) : Presenter<SdkView>(errorHandler, executor, view) {

    override fun attach() {

    }

    fun registerUser(userID: String, context: String) {
        heliosSdk.registerUser(userID, context, object : HeliosSdkImpl.RegisterUserResult {
            override fun onError() {
                view.showError("error")
            }

            override fun onResult(registerDataResponse: RegisterDataResponse) {
                view.showToken(registerDataResponse.token)
            }
        })
    }

    fun getToken() {
        heliosSdk.getToken(object : HeliosSdkImpl.GetTokenResult {
            override fun onError() {
                view.showError("error")
            }

            override fun onResult(token: String) {
                view.showToken(token)
            }
        })
    }

    fun removeToken() {
        heliosSdk.removeToken(object : HeliosSdkImpl.RemoveTokenResult {
            override fun onError() {
                onRetry(Error.NoInternet) { removeToken() }
            }

            override fun onResult(success: Success) {

            }
        })
    }
}

interface SdkView : View {
    fun showSuccess()
    fun showToken(token: String)
}
