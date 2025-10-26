package com.example.tbcworks.items

import com.example.tbcworks.enums.AddressType
import java.io.Serializable

data class AddressItem(
    val id: Int,
    val addressType: AddressType,
    val name: String,
    val description: String,
    val isSelected: Boolean = false
) : Serializable
