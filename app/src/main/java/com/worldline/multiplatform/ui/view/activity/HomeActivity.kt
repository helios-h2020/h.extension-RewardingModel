package com.worldline.multiplatform.ui.view.activity

import android.content.Context
import android.content.Intent
import com.worldline.multiplatform.R
import com.worldline.multiplatform.domain.model.Forecast
import com.worldline.multiplatform.ui.app.ACTIVITY_MODULE
import com.worldline.multiplatform.ui.presenter.HomePresenter
import com.worldline.multiplatform.ui.presenter.HomeView
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider


class HomeActivity : RootActivity<HomeView>(), HomeView {

    companion object {
        fun intent(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

    override val presenter: HomePresenter by instance()
    override val layoutResourceId: Int = R.layout.activity_home
    override val activityModule = Kodein.Module(ACTIVITY_MODULE) {
        bind<HomePresenter>() with provider {
            HomePresenter(
                repository = instance(),
                executor = instance(),
                errorHandler = instance(),
                view = this@HomeActivity
            )
        }
    }

    override fun initializeUI() {
        // Do nothing
    }

    override fun registerListeners() {
        // Do nothing
    }

    override fun showForecast(forecast: Forecast) {
        forecastDataSample.text = forecast.toString()
    }
}
