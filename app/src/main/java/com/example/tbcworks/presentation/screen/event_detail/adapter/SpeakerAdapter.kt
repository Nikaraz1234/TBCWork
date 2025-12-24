package com.example.tbcworks.presentation.screen.event_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tbcworks.databinding.ItemSpeakerBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.model.SpeakerModel

class SpeakerAdapter :
    ListAdapter<SpeakerModel, SpeakerAdapter.SpeakerViewHolder>(
        GenericDiffCallback(
            areItemsTheSameCheck = { old, new ->
                old.fullName == new.fullName
            },
            areContentsTheSameCheck = { old, new ->
                old == new
            }
        )
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSpeakerBinding.inflate(inflater, parent, false)
        return SpeakerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpeakerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SpeakerViewHolder(
        private val binding: ItemSpeakerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SpeakerModel) = with(binding) {

            tvName.text = item.fullName
            tvRole.text = item.role

            ivProfilePicture.load(item.imgUrl)
        }
    }
}
