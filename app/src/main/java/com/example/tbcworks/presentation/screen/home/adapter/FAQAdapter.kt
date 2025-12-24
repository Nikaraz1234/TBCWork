package com.example.tbcworks.presentation.screen.home.adapter

import androidx.recyclerview.widget.ListAdapter
import com.example.tbcworks.databinding.ItemAskedQuestionBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.screen.home.model.QaItem

class FAQAdapter : ListAdapter<QaItem, FAQAdapter.QaViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new -> old.question == new.question },
        areContentsTheSameCheck = { old, new -> old == new }
    )
) {

    inner class QaViewHolder(private val binding: ItemAskedQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: QaItem) = with(binding) {
            tvQuestion.text = item.question
            tvAnswer.text = item.answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QaViewHolder {
        val binding = ItemAskedQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
