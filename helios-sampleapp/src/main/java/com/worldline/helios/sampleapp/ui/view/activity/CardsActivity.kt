package com.worldline.helios.sampleapp.ui.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.wordline.helios.rewarding.sdk.domain.model.Card
import com.worldline.helios.sampleapp.R
import com.worldline.helios.sampleapp.ui.app.ACTIVITY_MODULE
import com.worldline.helios.sampleapp.ui.extension.toast
import com.worldline.helios.sampleapp.ui.presenter.CardsPresenter
import com.worldline.helios.sampleapp.ui.presenter.CardsView
import com.worldline.helios.sampleapp.ui.view.adapter.CardsAdapter
import kotlinx.android.synthetic.main.activity_cards.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider


class CardsActivity : RootActivity<CardsView>(), CardsView {

    private lateinit var mAdapter: CardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        CardsList.setHasFixedSize(true)
        CardsList.layoutManager = LinearLayoutManager(this)
        //Creates the CardsAdapter with a callback for an OnClick event.
        mAdapter = CardsAdapter {
            presenter.redeemCard(it.id)
        }
        CardsList.adapter = mAdapter
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

    override fun showError(error: String) {
        toast(error, Toast.LENGTH_LONG)
    }

    override fun showSuccess() {
        toast("Cards loaded.", Toast.LENGTH_LONG)
    }

    //Updates the list of cards.
    override fun showList(cards: List<Card>) {
        mAdapter.replace(cards)
    }

}
