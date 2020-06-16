package com.worldline.helios.rewardingstub.multiplatform.ui.view.activity

import android.content.Context
import android.content.Intent
import android.widget.Button
import com.worldline.helios.rewardingstub.multiplatform.R
import com.worldline.helios.rewardingstub.multiplatform.domain.model.Forecast
import com.worldline.helios.rewardingstub.multiplatform.ui.app.ACTIVITY_MODULE
import com.worldline.helios.rewardingstub.multiplatform.ui.presenter.CardsPresenter
import com.worldline.helios.rewardingstub.multiplatform.ui.presenter.CardsView
import kotlinx.android.synthetic.main.activity_cards.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider


class CardsActivity : RootActivity<CardsView>(), CardsView {

    companion object {
        fun intent(context: Context): Intent = Intent(context, CardsActivity::class.java)
    }

    override val presenter by instance<CardsPresenter>()
    override val layoutResourceId: Int = R.layout.activity_cards
    override val activityModule = Kodein.Module(ACTIVITY_MODULE) {
        bind<CardsPresenter>() with provider {
            CardsPresenter(
                repository = instance(),
                executor = instance(),
                errorHandler = instance(),
                view = this@CardsActivity
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
