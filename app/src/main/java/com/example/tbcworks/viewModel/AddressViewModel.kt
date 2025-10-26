package com.example.tbcworks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tbcworks.enums.AddressType
import com.example.tbcworks.items.AddressItem

class AddressViewModel : ViewModel() {



    private var id = 0

    private val initialItems = listOf(
        AddressItem(
            id = id++,
            addressType = AddressType.OFFICE,
            name = "My Office",
            description = DESCRIPTION
        ),
        AddressItem(
            id = id++,
            addressType = AddressType.HOME,
            name = "My Home",
            description = DESCRIPTION
        )
    )

    private val _items = MutableLiveData<List<AddressItem>>(initialItems)
    val items: LiveData<List<AddressItem>> get() = _items

    fun nextId(): Int = id++
    fun addAddress(address: AddressItem) {
        val currentList = _items.value ?: emptyList()
        _items.value = listOf(address) + currentList
    }

    fun updateAddress(updatedAddress: AddressItem) {
        val currentList = _items.value ?: return
        val index = currentList.indexOfFirst { it.id == updatedAddress.id }
        if (index != -1) {
            val newList = currentList.toMutableList()
            newList[index] = updatedAddress
            _items.value = newList
        }
    }

    fun deleteAddress(address: AddressItem) {
        val currentList = _items.value ?: return
        _items.value = currentList.filter { it.id != address.id }
    }

    companion object {
        const val DESCRIPTION = "SBI Building, street 3, Software Park"
    }
}
