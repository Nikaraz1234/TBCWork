package com.example.tbcworks.presentation.screen.register.adapter

import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ItemDigitBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.screen.register.model.DigitInput


class DigitAdapter(
    private val onComplete: (String) -> Unit
) : ListAdapter<DigitInput, DigitAdapter.DigitViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new -> old === new },
        areContentsTheSameCheck = { old, new -> old.value == new.value }
    )
) {

    inner class DigitViewHolder(val binding: ItemDigitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DigitInput, position: Int) {
            binding.etDigit.setText(item.value)

            binding.etDigit.apply {
                filters = arrayOf(InputFilter.LengthFilter(1))
                inputType = InputType.TYPE_CLASS_NUMBER

                addTextChangedListener {
                    item.value = it.toString()

                    if (it?.length == 1 && position < currentList.size - 1) {
                        recyclerView?.findViewHolderForAdapterPosition(position + 1)
                            ?.let { nextHolder ->
                                (nextHolder as DigitViewHolder).binding.etDigit.requestFocus()
                            }
                    }

                    val code = currentList.joinToString("") { it.value }
                    if (code.length == currentList.size && code.all { ch -> ch.isDigit() }) {
                        onComplete(code)
                    }
                }
            }
        }
    }

    private val recyclerView: RecyclerView? get() = _recyclerView
    private var _recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        _recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        _recyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DigitViewHolder {
        val binding = ItemDigitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DigitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DigitViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}

