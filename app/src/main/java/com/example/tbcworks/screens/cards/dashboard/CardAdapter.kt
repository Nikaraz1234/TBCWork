package com.example.tbcworks.screens.cards.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.CardItemBinding
import com.example.tbcworks.screens.cards.Card
import com.example.tbcworks.screens.cards.CardType

class CardAdapter: ListAdapter<Card, CardAdapter.CardViewHolder>(DIFFUTIL) {

    inner class CardViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            with(binding) {
                tvCardNumber.text = card.cardNumber
                tvCardHolderName.text = card.cardHolder
                tvValidDate.text = card.expires
                if(card.cardType == CardType.Visa){
                    clCard.setBackgroundResource(R.drawable.visa_card_bg)
                }else{
                    clCard.setBackgroundResource(R.drawable.mastercard_card_bg)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Card, newItem: Card) = oldItem == newItem
        }
    }
}
