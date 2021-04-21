package com.worldline.helios.sampleapp.ui.view.activity

import android.widget.CheckBox
import android.widget.Toast
import com.wordline.helios.rewarding.sdk.domain.Action
import com.worldline.helios.sampleapp.R
import com.worldline.helios.sampleapp.ui.app.ACTIVITY_MODULE
import com.worldline.helios.sampleapp.ui.extension.toast
import com.worldline.helios.sampleapp.ui.presenter.RecordPresenter
import com.worldline.helios.sampleapp.ui.presenter.RecordView
import kotlinx.android.synthetic.main.activity_record.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import java.util.*


class RecordActivity : RootActivity<RecordView>(), RecordView {

    override val presenter by instance<RecordPresenter>()
    override val layoutResourceId: Int = R.layout.activity_record
    override val activityModule = Kodein.Module(ACTIVITY_MODULE) {
        bind<RecordPresenter>() with provider {
            RecordPresenter(
                rewardingSdk = instance(),
                executor = instance(),
                errorHandler = instance(),
                view = this@RecordActivity
            )
        }
    }

    private val checkedActions: MutableList<Action> = mutableListOf()

    override fun initializeUI() {
        // Do nothing
        for (action in Action.values()) {
            val checkBox = CheckBox(this)
            checkBox.text = action.value
            checkBox.setOnClickListener {
                if (checkBox.isChecked) {
                    checkedActions.add(action)
                } else {
                    checkedActions.remove(action)
                }
            }
            recordLinearLayout.addView(checkBox)
        }
    }

    override fun registerListeners() {
        button_sendActivity.setOnClickListener {
            presenter.registerActivity(
                actions = checkedActions,
                date = Date().toString()
            );
        }
    }

    override fun showSuccess() {
        toast("Activity registered.", Toast.LENGTH_SHORT)
    }

}
