package com.example.tbcworks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.FragmentFieldsFormBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FieldsFormFragment : BaseFragment<FragmentFieldsFormBinding>() {
    private val viewModel: FormViewModel by viewModels()
    private lateinit var outerAdapter: OuterAdapter
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFieldsFormBinding {
        return FragmentFieldsFormBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            val json= resources.openRawResource(R.raw.fields)
                .bufferedReader()
                .use { it.readText() }

            viewModel.parseJson(json)

            val adapter = OuterAdapter(viewModel)
            rvForm.layoutManager = LinearLayoutManager(requireContext())
            rvForm.adapter = adapter

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.fieldGroups.collectLatest { groups ->
                        adapter.submitList(groups)
                    }
                }
            }


            btnRegister.setOnClickListener {
                if (viewModel.validateFields()) {
                    Snackbar.make(root, "Registration successful!", Snackbar.LENGTH_SHORT).show()
                } else {
                    val missingFields = viewModel.errorFields.joinToString(", ")
                    Snackbar.make(binding.root, "Please fill: $missingFields", Snackbar.LENGTH_LONG).show()
                }
            }


        }
    }




}