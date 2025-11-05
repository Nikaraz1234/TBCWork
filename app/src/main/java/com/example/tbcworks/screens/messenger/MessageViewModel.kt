package com.example.tbcworks.screens.messenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MessageViewModel : ViewModel() {
    private var id = 1
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> =_messages.asStateFlow()


    fun sendMessage(content: String){
        viewModelScope.launch {
            val newMessage= Message(
                id = id++,
                content = content,
            )
            _messages.value = listOf(newMessage) + _messages.value
        }
    }
}