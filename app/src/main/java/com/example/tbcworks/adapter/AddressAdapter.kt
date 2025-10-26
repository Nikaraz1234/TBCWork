package com.example.tbcworks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.AddressItemLayoutBinding
import com.example.tbcworks.enums.AddressType
import com.example.tbcworks.items.AddressItem

class AddressAdapter(
    private val onEditClick: (AddressItem) -> Unit,
    private val onLongClick: (AddressItem) -> Unit
) : ListAdapter<AddressItem, AddressAdapter.AddressViewHolder>(DIFFUTIL) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressAdapter.AddressViewHolder {
        val binding =
            AddressItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind()
    }

    inner class AddressViewHolder(private val binding: AddressItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val item = getItem(position)

            rbAddress.isChecked = item.isSelected
            btnEdit.isEnabled = item.isSelected

            rbAddress.setOnClickListener {
                val newList = currentList.map { it.copy(isSelected = it.id == item.id) }
                submitList(newList)
            }

            btnEdit.setOnClickListener {
                onEditClick(item)
            }

            root.setOnLongClickListener {
                onLongClick(item)
                true
            }

            if(item.addressType == AddressType.HOME){
                ivAddress.setImageResource(R.drawable.address_house)
            }else{
                ivAddress.setImageResource(R.drawable.address_building)
            }

            tvName.text = item.name
            tvDescription.text = item.description


        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<AddressItem>() {
            override fun areItemsTheSame(oldItem: AddressItem, newItem: AddressItem) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: AddressItem, newItem: AddressItem) = oldItem == newItem
        }
    }
}