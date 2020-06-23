package com.worldline.helios.sampleapp.ui.view.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.worldline.helios.sampleapp.ui.extension.hideMe
import com.worldline.helios.sampleapp.ui.extension.showMe
import com.worldline.helios.sampleapp.ui.extension.snackbar
import com.worldline.helios.sampleapp.ui.extension.toast
import com.worldline.helios.sampleapp.ui.presenter.Presenter
import kotlinx.android.synthetic.main.view_progress.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.subKodein
import com.worldline.helios.sampleapp.ui.presenter.View as PresenterView

abstract class RootActivity<out V : PresenterView> : AppCompatActivity(), KodeinAware,
    PresenterView {

    private val progress: View by lazy { progressView }

    abstract val presenter: Presenter<V>

    abstract val layoutResourceId: Int

    abstract val activityModule: Kodein.Module

    override val kodein by subKodein(kodein()) {
        import(activityModule)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)

        initializeUI()
        registerListeners()

        presenter.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    abstract fun initializeUI()

    abstract fun registerListeners()

    override fun showError(error: String) = toast(error)

    override fun showProgress() = progress.showMe()

    override fun hideProgress() = progress.hideMe()

    override fun showRetry(error: String, f: () -> Unit) {
        val viewGroup =
            (this.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

        snackbar(viewGroup, message = error, retryCallback = { f() })
    }
}
