package com.example.tbcworks.screens.addressList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.adapter.AddressAdapter
import com.example.tbcworks.databinding.FragmentAddressListBinding
import com.example.tbcworks.items.AddressItem
import com.example.tbcworks.viewModel.AddressViewModel

class AddressListFragment : BaseFragment<FragmentAddressListBinding>() {
    private lateinit var adapter: AddressAdapter
    private val viewModel: AddressViewModel by viewModels()


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddressListBinding {
        return FragmentAddressListBinding.inflate(inflater, container, false)
    }

    override fun bind() = with(binding){


        adapter = AddressAdapter(
            onEditClick = {addressItem ->
                val action = AddressListFragmentDirections
                    .actionShopListFragmentToAddressFormFragment(addressItem)

                findNavController().navigate(action)
            },
            onLongClick = {addressItem ->
                viewModel.deleteAddress(addressItem)
            }
        )

        rvShopList.adapter = adapter
        rvShopList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.items.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list.toList())
        }

        setFragmentResultListener("address") { _, bundle ->
            val address = bundle.getSerializable("address") as? AddressItem ?: return@setFragmentResultListener

            if (address.id == -1) {
                val newAddress = address.copy(id = viewModel.nextId())
                viewModel.addAddress(newAddress)
            } else {
                viewModel.updateAddress(address)
            }
        }


    }

    override fun listeners() = with(binding){
        btnAddAddress.setOnClickListener {
            addUser()
        }
        btnBack.setOnClickListener {

        }
    }
    private fun addUser(){
        val action = AddressListFragmentDirections
            .actionShopListFragmentToAddressFormFragment(null)
        findNavController().navigate(action)

    }





}