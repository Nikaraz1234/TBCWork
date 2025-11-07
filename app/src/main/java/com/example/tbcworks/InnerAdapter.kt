package com.example.tbcworks

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ItemChooserBinding
import com.example.tbcworks.databinding.ItemInputBinding

class InnerAdapter(
    private val viewModel: FormViewModel
) : ListAdapter<Field, RecyclerView.ViewHolder>(DIFFUTIL) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_INPUT -> InputViewHolder(
                ItemInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            TYPE_CHOOSER -> ChooserViewHolder(
                ItemChooserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> InputViewHolder(
                ItemInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

    }

    inner class InputViewHolder(private val binding: ItemInputBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(field: Field) = with(binding){
            etInput.hint = field.hint
            etInput.inputType = if (field.keyboard == NUMBER) InputType.TYPE_CLASS_NUMBER else InputType.TYPE_CLASS_TEXT

            etInput.setText(viewModel.getFieldValue(field.field_id))

            etInput.addTextChangedListener { text ->
                viewModel.updateFieldValue(field.field_id, text.toString())
            }
        }

    }

    inner class ChooserViewHolder(private val binding: ItemChooserBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(field: Field) = with(binding){
            val options = when(field.hint) {
                GENDER -> listOf("Male", "Female", "Other")
                BIRTHDAY -> listOf("01/01/2000", "02/02/2001")
                else -> listOf("Option 1", "Option 2")
            }
            val adapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, options)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter


            val currentValue = viewModel.getFieldValue(field.field_id)
            if (currentValue.isNotBlank()) {
                val position = options.indexOf(currentValue)
                if (position >= 0) spinner.setSelection(position)
            }

        }
    }
    override fun getItemViewType(position: Int) = when (getItem(position).field_type) {
        INPUT -> TYPE_INPUT
        CHOOSER -> TYPE_CHOOSER
        else -> TYPE_INPUT
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val field = getItem(position)
        when(holder) {
            is InputViewHolder -> holder.bind(field)
            is ChooserViewHolder -> holder.bind(field)
        }
    }

    companion object {
        const val TYPE_INPUT = 1
        const val TYPE_CHOOSER = 2
        const val NUMBER = "number"
        const val GENDER = "Gender"
        const val BIRTHDAY = "Birthday"
        const val INPUT = "input"
        const val CHOOSER = "chooser"
        val DIFFUTIL = object : DiffUtil.ItemCallback<Field>() {
            override fun areItemsTheSame(oldItem: Field, newItem: Field) = oldItem.field_id == newItem.field_id
            override fun areContentsTheSame(oldItem: Field, newItem: Field) = oldItem == newItem
        }
    }
}