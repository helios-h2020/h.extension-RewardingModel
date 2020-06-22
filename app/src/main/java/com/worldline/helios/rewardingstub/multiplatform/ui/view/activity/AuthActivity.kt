package com.worldline.helios.rewardingstub.multiplatform.ui.view.activity

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.worldline.helios.rewardingstub.multiplatform.R
import com.worldline.helios.rewardingstub.multiplatform.ui.app.ACTIVITY_MODULE
import com.worldline.helios.rewardingstub.multiplatform.ui.presenter.AuthPresenter
import com.worldline.helios.rewardingstub.multiplatform.ui.presenter.AuthView
import kotlinx.android.synthetic.main.activity_auth.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider


class AuthActivity : RootActivity<AuthView>(), AuthView {

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }*/

    companion object {
        fun intent(context: Context): Intent = Intent(context, AuthActivity::class.java)
    }

    override val presenter by instance<AuthPresenter>()
    override val layoutResourceId: Int = R.layout.activity_auth
    override val activityModule = Kodein.Module(ACTIVITY_MODULE) {
        bind<AuthPresenter>() with provider {
            AuthPresenter(
                repository = instance(),
                executor = instance(),
                errorHandler = instance(),
                view = this@AuthActivity
            )
        }
    }

    override fun initializeUI() {
        // Do nothing
    }

    override fun registerListeners() {
        // Do nothing
        button_sendAuth.setOnClickListener() { v ->
            presenter.registerUser(userID = editUserID.text.toString(), context = editContext.text.toString());
        }
    }

    override fun showSuccess() {
        Toast.makeText(applicationContext,"User registered.",Toast.LENGTH_SHORT).show()
    }

}
