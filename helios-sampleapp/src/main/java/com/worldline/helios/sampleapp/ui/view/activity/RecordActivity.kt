package com.worldline.helios.sampleapp.ui.view.activity

import android.content.Context
import android.content.Intent
import android.view.View
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

    companion object {
        fun intent(context: Context): Intent = Intent(context, CardsActivity::class.java)
    }

    override val presenter by instance<RecordPresenter>()
    override val layoutResourceId: Int = R.layout.activity_record
    override val activityModule = Kodein.Module(ACTIVITY_MODULE) {
        bind<RecordPresenter>() with provider {
            RecordPresenter(
                repository = instance(),
                executor = instance(),
                errorHandler = instance(),
                view = this@RecordActivity
            )
        }
    }

    private val checkedActions: MutableList<String> = mutableListOf<String>()

    override fun initializeUI() {
        // Do nothing
        for (action in Action.values()) {
            val checkBox = CheckBox(this)
            checkBox.text = action.value
            checkBox.setOnClickListener {
                if (checkBox.isChecked) {
                    checkedActions.add(checkBox.text.toString())
                } else {
                    checkedActions.remove(checkBox.text.toString())
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
