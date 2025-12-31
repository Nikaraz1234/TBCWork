package com.example.tbcworks.presentation.screen.search

import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.FragmentSearchBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screen.search.adapter.CategoryAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {

    private val viewModel: SearchViewModel by viewModels()
    private val categoryAdapter by lazy {
        CategoryAdapter { category ->
            viewModel.onEvent(SearchEvent.OnCategoryClick(category))
        }
    }

    override fun bind() {
        setupRecyclerView()
        observeViewModel()
        setupSearchListener()
    }

    private fun setupRecyclerView() {
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryAdapter

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        }
    }

    private fun observeViewModel() {
        collectStateFlow(viewModel.uiState) { state ->
            categoryAdapter.submitList(state.visibleCategories)
            binding.progressBar.isVisible = state.isLoading
        }


        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is SearchSideEffect.ShowMessage -> {
                    Snackbar.make(binding.root, effect.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupSearchListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.onEvent(SearchEvent.OnQueryChange(s.toString()))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
