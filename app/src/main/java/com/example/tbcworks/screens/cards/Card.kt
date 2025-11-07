package com.example.tbcworks.screens.cards

import kotlinx.serialization.Serializable


@Serializable
data class Card(
    val id: Int = -1,
    val cardNumber: String,
    val cardHolder: String,
    val expires: String,
    val cvv: Int,
    val cardType: CardType
) : java.io.Serializable

enum class CardType : java.io.Serializable{
    Mastercard,
    Visa
}