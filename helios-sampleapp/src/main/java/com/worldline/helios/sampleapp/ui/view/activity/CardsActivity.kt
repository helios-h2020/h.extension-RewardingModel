package com.worldline.helios.sampleapp.ui.view.activity

import android.content.Context
import android.content.Intent
import com.worldline.helios.sampleapp.R
import com.worldline.helios.sampleapp.ui.app.ACTIVITY_MODULE
import com.worldline.helios.sampleapp.ui.presenter.CardsPresenter
import com.worldline.helios.sampleapp.ui.presenter.CardsView
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
