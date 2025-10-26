package com.example.tbcworks.screens.addressForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.databinding.FragmentAddressFormBinding
import com.example.tbcworks.enums.AddressType
import com.example.tbcworks.items.AddressItem
import com.example.tbcworks.viewModel.AddressViewModel

class AddressFormFragment : BaseFragment<FragmentAddressFormBinding>() {
    private val viewModel: AddressViewModel by viewModels()
    private val args: AddressFormFragmentArgs by navArgs()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddressFormBinding {
        return FragmentAddressFormBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun listeners() = with(binding){
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        btnSubmit.setOnClickListener {
            submit()
        }
    }
    private fun submit() {
        with(binding){
            val name = etName.text.toString().trim()
            val desc = etDescription.text.toString().trim()
            val selectedType = binding.spinnerAddressType.selectedItem.toString()
            val type = when (selectedType) {
                HOME -> AddressType.HOME
                OFFICE -> AddressType.OFFICE
                else -> AddressType.HOME
            }

            if (!validateInputs(name, desc)) return

            val newAddress = if (binding.btnSubmit.text == EDIT_ADDRESS) {
                editUser(name, desc, type)
            } else {
                addAddress(name, desc, type)
            }
            setFragmentResult("address", bundleOf("address" to newAddress))
            findNavController().popBackStack()
        }
        

    }
    private fun validateInputs(name:String, desc: String): Boolean{
        var isValid = true
        if (name.isEmpty()) {
            binding.etName.error = NAME_EMPTY_ERROR
            isValid = false
        }

        if (desc.isEmpty()) {
            binding.etDescription.error = DESC_EMPTY_ERROR
            isValid = false
        }

        return isValid
    }
    private fun editUser(name: String, desc: String, type: AddressType): AddressItem = with(binding) {
        val currentAddress = args.addressItem ?: throw Exception()

        val updatedAddress = currentAddress.copy(
            name = name,
            description = desc,
            addressType = type
        )

        return@with updatedAddress
    }

    private fun addAddress(name: String, desc: String, type: AddressType) : AddressItem = with(binding){

        return@with AddressItem(
        id = -1,
        name = name,
        description = desc,
        addressType = type
        )
    }
    override fun bind() = with(binding){
        val item = args.addressItem

        if (item!= null){
            tvTitle.text = EDIT_ADDRESS
            etName.setText(item.name)
            etDescription.setText(item.description)
            if(item.addressType == AddressType.HOME){
                spinnerAddressType.setSelection(0)
            }else{
                spinnerAddressType.setSelection(1)
            }
            btnSubmit.text = EDIT_ADDRESS
        }
    }
    companion object {
        const val EDIT_ADDRESS = "Edit Address"
        const val NAME_EMPTY_ERROR = "Name cannot be empty"
        const val DESC_EMPTY_ERROR = "Description cannot be empty"
        const val HOME = "Home"
        const val OFFICE = "Office"
    }


}