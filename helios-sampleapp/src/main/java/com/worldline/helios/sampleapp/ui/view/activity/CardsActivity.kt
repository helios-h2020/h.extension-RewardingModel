package com.worldline.helios.sampleapp.ui.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wordline.helios.rewarding.sdk.domain.model.Card
import com.worldline.helios.sampleapp.R
import com.worldline.helios.sampleapp.ui.app.ACTIVITY_MODULE
import com.worldline.helios.sampleapp.ui.extension.toast
import com.worldline.helios.sampleapp.ui.presenter.CardsPresenter
import com.worldline.helios.sampleapp.ui.presenter.CardsView
import com.worldline.helios.sampleapp.ui.view.adapter.RecyclerAdapter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider


class CardsActivity : RootActivity<CardsView>(), CardsView {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        setUpRecyclerView()
    }

    fun setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.CardsList) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        //Creates the RecyclerAdapter with a callback for an OnClick event.
        mAdapter = RecyclerAdapter {
            presenter.redeemCard(it.id)
        }
        mRecyclerView.adapter = mAdapter
    }

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
