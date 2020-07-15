package com.worldline.helios.sampleapp.ui.presenter

import com.wordline.helios.rewarding.sdk.RecordRewardableActivityCallback
import com.wordline.helios.rewarding.sdk.RewardingSdk
import com.wordline.helios.rewarding.sdk.domain.Action
import com.wordline.helios.rewarding.sdk.domain.model.RewardableActivity
import com.worldline.helios.sampleapp.ui.error.ErrorHandler
import com.worldline.helios.sampleapp.ui.executor.Executor

class RecordPresenter(
    private val rewardingSdk: RewardingSdk,
    errorHandler: ErrorHandler,
    executor: Executor,
    view: RecordView
) : Presenter<RecordView>(errorHandler, executor, view) {

    override fun attach() {

    }

    override fun detach() {
        rewardingSdk.cancel()
    }

    //If the registerActivity call works It'll show a toast with a message.
    fun registerActivity(actions: List<Action>, date: String) {
        val rewardableActivities: MutableList<RewardableActivity> = mutableListOf()
        for (action in actions) {
            rewardableActivities.add(RewardableActivity(action = action.value, date = date))
        }
        rewardingSdk.recordRewardableActivity(rewardableActivities, object: RecordRewardableActivityCallback {
            override fun onError() {
                view.showError("error")
            }

            override fun onSuccess() {
                view.showSuccess()
            }

        })
    }
}

interface RecordView : View {
    fun showSuccess()
}
