package com.worldline.helios.rewardingstub.multiplatform.ui.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.worldline.helios.rewardingstub.multiplatform.R
import com.worldline.helios.rewardingstub.multiplatform.ui.extension.hideMe
import com.worldline.helios.rewardingstub.multiplatform.ui.extension.showMe
import com.worldline.helios.rewardingstub.multiplatform.ui.extension.snackbar
import com.worldline.helios.rewardingstub.multiplatform.ui.presenter.Presenter
import kotlinx.android.synthetic.main.view_progress.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.subKodein
import org.kodein.di.android.x.kodein
import com.worldline.helios.rewardingstub.multiplatform.ui.presenter.View as PresenterView

abstract class RootDialog<out T : PresenterView> : DialogFragment(), KodeinAware, PresenterView {

    abstract val layoutResourceId: Int

    abstract val presenter: Presenter<T>

    abstract val fragmentModule: Kodein.Module

    override val kodein by subKodein(kodein()) {
        import(fragmentModule)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog?.window?.attributes?.windowAnimations = R.style.FullScreenDialogAnimation
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutResourceId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI()
        registerListeners()
        presenter.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    abstract fun initializeUI()

    abstract fun registerListeners()

    override fun showProgress() {
        progressView.showMe()
    }

    override fun hideProgress() {
        progressView.hideMe()
    }

    override fun showRetry(error: String, f: () -> Unit) {
        view?.let { activity?.snackbar(it, message = error, retryCallback = { f() }) }
    }
}
