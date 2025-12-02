package com.example.tbcworks.presentation.screens.lock_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ItemNumberLayoutBinding
import com.example.tbcworks.presentation.common.BaseDiffUtil
import com.example.tbcworks.presentation.screens.lock_screen.models.NumberModel

class NumbersAdapter(
    private val onClick: (NumberModel) -> Unit
) : ListAdapter<NumberModel, NumbersAdapter.NumberViewHolder>(
    BaseDiffUtil(
        areItemsTheSameCallback = { old, new -> old === new },
        areContentsTheSameCallback = { old, new -> old == new }
    )
) {

    inner class NumberViewHolder(private val binding: ItemNumberLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NumberModel) {
            with(binding){
                when (item.type) {
                    NumberModel.BtnType.NUMBER -> {
                        tvNumber.text = item.value
                        ivIcon.visibility = View.GONE
                    }
                    NumberModel.BtnType.BACKSPACE -> {
                        tvNumber.text = ""
                        ivIcon.visibility = View.VISIBLE
                        ivIcon.setImageResource(R.drawable.ic_backspace)
                    }
                    NumberModel.BtnType.FINGERPRINT -> {
                        tvNumber.text = ""
                        ivIcon.visibility = View.VISIBLE
                        ivIcon.setImageResource(R.drawable.ic_fingerprint)
                    }
                }

                root.setOnClickListener { onClick(item) }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val binding = ItemNumberLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
