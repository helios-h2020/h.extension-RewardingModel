package com.worldline.helios.sampleapp.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wordline.helios.rewarding.sdk.domain.model.Card
import com.worldline.helios.sampleapp.R
import kotlinx.android.synthetic.main.item_card_list.view.*

class RecyclerAdapter(private var onRedeemClickListener: (Card) -> Unit) : RootAdapter<Card>() {

    override val itemLayoutId: Int = R.layout.item_card_list

    override fun viewHolder(view: View): RootViewHolder<Card> =
        ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootViewHolder<Card> {
        val view = LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)

        val viewHolder = viewHolder(view)
        //Setting the redeemedButton's listener with the callback onRedeemClickListener
        viewHolder.itemView.redeemedButton.setOnClickListener {
            onRedeemClickListener(items[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    inner class ViewHolder(view: View) : RootAdapter.RootViewHolder<Card>(itemView = view) {

        //Relates the content with the item.
        override fun bind(card: Card){
            itemView.cardId.text = card.id
            itemView.tokens.text = card.tokens

        }

    }

}
