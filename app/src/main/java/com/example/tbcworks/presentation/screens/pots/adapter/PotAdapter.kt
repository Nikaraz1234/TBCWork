package com.example.tbcworks.presentation.screens.pots.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ItemPotLayoutBinding
import com.example.tbcworks.presentation.common.GenericDiffUtil
import com.example.tbcworks.presentation.screens.pots.model.PotModel

class PotAdapter(
    private val onAddMoneyClick: (PotModel) -> Unit,
    private val onWithdrawClick: (PotModel) -> Unit,
    private val onOptionsClick: (PotModel) -> Unit
) : ListAdapter<PotModel, PotAdapter.PotViewHolder>(
    GenericDiffUtil(
        areItemsSame = { old, new -> old.id == new.id },
        areContentsSame = { old, new -> old == new }
    )
) {

    inner class PotViewHolder(private val binding: ItemPotLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pot: PotModel) = with(binding) {
            titleText.text = pot.title
            amountText.text = DOLLAR_SIGN.plus(pot.amount)
            progressBar.progress = pot.progressPercent.toInt()
            progressPercent.text = PERCENT_SIGN.plus(String.format("%.2f", pot.progressPercent))
            progressTarget.text = TARGET_TEXT.plus(pot.targetAmount)

            addMoneyButton.setOnClickListener { onAddMoneyClick(pot) }
            withdrawButton.setOnClickListener { onWithdrawClick(pot) }
            optionsIcon.setOnClickListener { onOptionsClick(pot) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PotViewHolder {
        val binding = ItemPotLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PotViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private const val DOLLAR_SIGN = "$"
        private const val PERCENT_SIGN = "%"
        private const val TARGET_TEXT = "Target of $"
    }
}
