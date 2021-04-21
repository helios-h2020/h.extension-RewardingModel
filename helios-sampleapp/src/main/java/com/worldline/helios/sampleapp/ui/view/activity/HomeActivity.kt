package com.worldline.helios.sampleapp.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.worldline.helios.sampleapp.R
import com.worldline.helios.sampleapp.ui.app.ACTIVITY_MODULE
import com.worldline.helios.sampleapp.ui.presenter.HomePresenter
import com.worldline.helios.sampleapp.ui.presenter.HomeView
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider


class HomeActivity : RootActivity<HomeView>(), HomeView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.removeToken()
    }

    override val presenter by instance<HomePresenter>()
    override val layoutResourceId: Int = R.layout.activity_home
    override val activityModule = Kodein.Module(ACTIVITY_MODULE) {
        bind<HomePresenter>() with provider {
            HomePresenter(
                rewardingSdk = instance(),
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
        buttonRegister.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        buttonRecord.setOnClickListener {
            val intent = Intent(this, RecordActivity::class.java)
            startActivity(intent)
        }

        buttonToken.setOnClickListener {
            presenter.getToken()
        }
    }

    override fun showToken(token: String) {
        Toast.makeText(applicationContext, token, Toast.LENGTH_SHORT).show()
    }

}
