package com.example.tbcworks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class OuterAdapter(
    private val viewModel: FormViewModel
) : ListAdapter<FieldGroup, OuterAdapter.GroupViewHolder>(DIFFUTIL()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group)
    }

    class GroupViewHolder(itemView: View, private val viewModel: FormViewModel) :
        RecyclerView.ViewHolder(itemView) {

        private val innerRecyclerView: RecyclerView = itemView.findViewById(R.id.innerRecyclerView)
        private val innerAdapter = InnerAdapter(viewModel)

        init {
            innerRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            innerRecyclerView.adapter = innerAdapter
            innerRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    itemView.context,
                    LinearLayoutManager.VERTICAL
                )
            )

        }

        fun bind(group: FieldGroup) {
            innerAdapter.submitList(group.fields)
        }
    }

    class DIFFUTIL : DiffUtil.ItemCallback<FieldGroup>() {
        override fun areItemsTheSame(oldItem: FieldGroup, newItem: FieldGroup) =
            oldItem.fields == newItem.fields

        override fun areContentsTheSame(oldItem: FieldGroup, newItem: FieldGroup) =
            oldItem == newItem
    }
}
