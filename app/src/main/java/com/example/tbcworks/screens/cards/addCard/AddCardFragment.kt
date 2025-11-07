package com.example.tbcworks.screens.cards.addCard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.databinding.FragmentAddCardBinding
import com.example.tbcworks.screens.cards.Card
import com.example.tbcworks.screens.cards.CardType

class AddCardFragment : BaseFragment<FragmentAddCardBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddCardBinding {
        return FragmentAddCardBinding.inflate(inflater, container, false)
    }

    override fun listeners() {

        with(binding){

            btnAdd.setOnClickListener {
                val newCard = Card(
                    cardNumber = etCardNumber.text.toString(),
                    cardHolder = etCardholder.text.toString(),
                    expires = etDate.text.toString(),
                    cvv = etCvv.text.toString().toInt(),
                    cardType = if(rbVisa.isChecked) CardType.Visa else CardType.Mastercard
                )
                parentFragmentManager.setFragmentResult(
                    "cardRequestKey",
                    bundleOf("card" to newCard)
                )
                findNavController().popBackStack()
            }

        }
    }

}