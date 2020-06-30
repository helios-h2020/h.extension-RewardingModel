package com.worldline.helios.sampleapp.ui.view.activity

import android.content.Context
import android.content.Intent
import com.worldline.helios.sampleapp.ui.app.ACTIVITY_MODULE
import com.worldline.helios.sampleapp.ui.extension.toast
import com.worldline.helios.sampleapp.ui.presenter.SdkPresenter
import com.worldline.helios.sampleapp.ui.presenter.SdkView
import kotlinx.android.synthetic.main.activity_auth.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider


class SdkActivity : RootActivity<SdkView>(), SdkView {

    companion object {
        fun intent(context: Context): Intent = Intent(context, SdkActivity::class.java)
    }

    override val presenter by instance<SdkPresenter>()
    override val layoutResourceId: Int = com.worldline.helios.sampleapp.R.layout.activity_sdk
    override val activityModule = Kodein.Module(ACTIVITY_MODULE) {
        bind<SdkPresenter>() with provider {
            SdkPresenter(
                heliosSdk = instance(),
                executor = instance(),
                errorHandler = instance(),
                view = this@SdkActivity
            )
        }
    }

    override fun initializeUI() {
        // Do nothing
    }

    override fun registerListeners() {
        button_sendAuth.setOnClickListener() { v ->
            presenter.registerUser(
                userID = editUserID.text.toString(),
                context = editContext.text.toString()
            )
        }
    }

    override fun showSuccess() {
        toast("User registered.")
    }

    override fun showToken(token: String) {
        toast("Token: $token")
    }

}
