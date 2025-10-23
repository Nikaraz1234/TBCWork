package com.example.tbcworks.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.R
import com.example.tbcworks.adapter.ShopAdapter
import com.example.tbcworks.databinding.FragmentShopListBinding
import com.example.tbcworks.enums.CategoryType
import com.example.tbcworks.viewModel.ShopViewModel

class ShopListFragment : BaseFragment<FragmentShopListBinding>() {
    private lateinit var adapter: ShopAdapter
    private val viewModel: ShopViewModel by viewModels()
    private var selectedButton: View? = null


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShopListBinding {
        return FragmentShopListBinding.inflate(inflater, container, false)
    }

    override fun bind() = with(binding){

        selectedButton = btnAll

        adapter = ShopAdapter()

        rvShopList.adapter = adapter
        rvShopList.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.items.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }


    }

    override fun listeners() = with(binding){
        btnAll.setOnClickListener {
            viewModel.filterByCategory(CategoryType.ALL)
            changeCategory(it)
        }
        btnParty.setOnClickListener {
            viewModel.filterByCategory(CategoryType.PARTY)
            changeCategory(it)
        }
        btnCamping.setOnClickListener {
            viewModel.filterByCategory(CategoryType.CAMPING)
            changeCategory(it)
        }
        btnCategoryOne.setOnClickListener {
            viewModel.filterByCategory(CategoryType.CATEGORY1)
            changeCategory(it)
        }
        btnCategoryTwo.setOnClickListener {
            viewModel.filterByCategory(CategoryType.CATEGORY2)
            changeCategory(it)
        }
        btnCategoryThree.setOnClickListener {
            viewModel.filterByCategory(CategoryType.CATEGORY3)
            changeCategory(it)
        }
    }
    private fun changeCategory(btn: View){
        selectedButton?.setBackgroundResource(R.drawable.grey_bg)
        btn.setBackgroundResource(R.drawable.green_bg)
        selectedButton?.isEnabled = true
        selectedButton = btn
        selectedButton?.isEnabled = false
    }




}