package com.worldline.helios.sampleapp.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.wordline.helios.rewarding.sdk.domain.model.Card
import com.worldline.helios.sampleapp.R

class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var cards: MutableList<Card>  = ArrayList()
    lateinit var context: Context
    lateinit var action: View.OnClickListener

    fun RecyclerAdapter(cards : MutableList<Card>, context: Context/*, action: View.OnClickListener*/){
        this.cards = cards
        this.context = context
        //this.action = action
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cards.get(position)
        //holder.itemView.setOnClickListener({action.onClick(it)})
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_card_list, parent, false))
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardId = view.findViewById(R.id.cardId) as TextView
        var tokens = view.findViewById(R.id.tokens) as TextView
        val redeemButton = view.findViewById(R.id.redeemedButton) as Button

        fun bind(card: Card, context: Context){
            cardId.text = card.id
            tokens.text = card.tokens
            itemView.setOnClickListener(View.OnClickListener { Toast.makeText(context, card.id, Toast.LENGTH_SHORT).show() })
            redeemButton.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "Redeem", Toast.LENGTH_SHORT).show()
            })
        }
    }

}
