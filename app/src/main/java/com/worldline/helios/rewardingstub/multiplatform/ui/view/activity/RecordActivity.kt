package com.worldline.helios.rewardingstub.multiplatform.ui.view.activity

import android.content.Context
import android.content.Intent
import android.widget.Button
import com.worldline.helios.rewardingstub.multiplatform.R
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Forecast
import com.worldline.helios.rewardingstub.multiplatform.ui.app.ACTIVITY_MODULE
import com.worldline.helios.rewardingstub.multiplatform.ui.presenter.RecordPresenter
import com.worldline.helios.rewardingstub.multiplatform.ui.presenter.RecordView
import kotlinx.android.synthetic.main.activity_record.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider


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

    override fun initializeUI() {
        // Do nothing
    }

    override fun registerListeners() {
        // Do nothing

    }

}
