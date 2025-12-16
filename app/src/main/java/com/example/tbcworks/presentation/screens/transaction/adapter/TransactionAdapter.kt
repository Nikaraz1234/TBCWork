package com.example.tbcworks.presentation.screens.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ItemTransactionLayoutBinding
import com.example.tbcworks.presentation.extension.toDate
import com.example.tbcworks.presentation.screens.transaction.model.TransactionModel

class TransactionAdapter :
    ListAdapter<TransactionModel, TransactionAdapter.TransactionViewHolder>(DiffCallback) {

    inner class TransactionViewHolder(
        private val binding: ItemTransactionLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TransactionModel) = with(binding) {
            tvName.text = item.receiverEmail
            tvPurpose.text = item.purpose
            tvValue.text = DOLLAR_SIGN.plus(item.value.toString())
            tvDate.text = item.date.toLong().toDate()

            ivProfile.load(item.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallback : DiffUtil.ItemCallback<TransactionModel>() {
        override fun areItemsTheSame(oldItem: TransactionModel, newItem: TransactionModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TransactionModel, newItem: TransactionModel): Boolean =
            oldItem == newItem
    }
    companion object{
        const val DOLLAR_SIGN = "$"
    }
}
