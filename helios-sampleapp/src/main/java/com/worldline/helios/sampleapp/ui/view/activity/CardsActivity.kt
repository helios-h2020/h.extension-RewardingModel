package com.worldline.helios.sampleapp.ui.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wordline.helios.rewarding.sdk.domain.model.Card
import com.worldline.helios.sampleapp.R
import com.worldline.helios.sampleapp.ui.app.ACTIVITY_MODULE
import com.worldline.helios.sampleapp.ui.presenter.CardsPresenter
import com.worldline.helios.sampleapp.ui.presenter.CardsView
import com.worldline.helios.sampleapp.ui.view.adapter.RecyclerAdapter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider


class CardsActivity : RootActivity<CardsView>(), CardsView {

    lateinit var mRecyclerView: RecyclerView
    val mAdapter: RecyclerAdapter = RecyclerAdapter()
    //lateinit var action: View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.redeemCard("5ed639880973ca758c8c3969")
        /*action = View.OnClickListener {
            //Things you want to do it in your On Click you can log
            println("activity")
        }*/
        setContentView(R.layout.activity_cards)
        setUpRecyclerView()
    }

    fun setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.CardsList) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter.RecyclerAdapter(getCards(), this/*, action*/)
        mRecyclerView.adapter = mAdapter
    }

    fun getCards(): MutableList<Card> {
        var cards: MutableList<Card> = ArrayList()
        cards.add(Card("5ed6397d0973ca758c8c3968", "0.75"))
        cards.add(Card("5ed639880973ca758c8c3969", "6"))
        cards.add(Card("5ed639ffc7aded0a4cf9fd72", "1"))
        cards.add(Card("5ed639ffc7aded021213fd72", "2"))
        return cards
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

}
