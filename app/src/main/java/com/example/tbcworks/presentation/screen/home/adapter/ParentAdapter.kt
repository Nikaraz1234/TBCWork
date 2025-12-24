package com.example.tbcworks.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ItemSectionBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.screen.home.model.CategoryModel
import com.example.tbcworks.presentation.model.EventModel
import com.example.tbcworks.presentation.screen.home.model.QaItem
import com.example.tbcworks.presentation.screen.home.model.Section

class   ParentAdapter(
    private val onUpcomingClick: (EventModel) -> Unit,
    private val onCategoryClick: (String) -> Unit,
    private val onTrendingClick: (EventModel) -> Unit
) : ListAdapter<Section, ParentAdapter.SectionViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new -> old.title == new.title },
        areContentsTheSameCheck = { old, new -> old.items == new.items }
    )
) {

    inner class SectionViewHolder(private val binding: ItemSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var adapter: RecyclerView.Adapter<*>? = null

        fun bind(section: Section) = with(binding) {
            tvSectionTitle.text = section.title
            tvViewAll.visibility =
                if (section.type == Section.SectionType.FAQ) View.GONE else View.VISIBLE

            if (adapter == null) {
                adapter = when (section.type) {
                    Section.SectionType.UPCOMING -> UpcomingAdapter(onUpcomingClick)
                    Section.SectionType.CATEGORY -> CategoryAdapter(onCategoryClick)
                    Section.SectionType.TRENDING -> TrendingAdapter(onTrendingClick)
                    Section.SectionType.FAQ -> FAQAdapter()
                } as RecyclerView.Adapter<*>?

                rvSectionItems.layoutManager = LinearLayoutManager(
                    root.context,
                    if (section.type != Section.SectionType.FAQ) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL,
                    false
                )
                rvSectionItems.adapter = adapter
            }
            when (section.type) {
                Section.SectionType.UPCOMING ->
                    (adapter as UpcomingAdapter).submitList(
                        section.items.filterIsInstance<EventModel>()
                    )
                Section.SectionType.CATEGORY ->
                    (adapter as CategoryAdapter).submitList(
                        section.items.filterIsInstance<CategoryModel>()
                    )
                Section.SectionType.TRENDING ->
                    (adapter as TrendingAdapter).submitList(
                        section.items.filterIsInstance<EventModel>()
                    )
                Section.SectionType.FAQ ->
                    (adapter as FAQAdapter).submitList(
                        section.items.filterIsInstance<QaItem>()
                    )
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val binding = ItemSectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
