package com.example.tbcworks.screens.messenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.databinding.FragmentMessengerBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MessengerFragment : BaseFragment<FragmentMessengerBinding>() {
    private val viewModel: MessageViewModel by viewModels()
    private lateinit var adapter: MessageAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMessengerBinding {
        return FragmentMessengerBinding.inflate(inflater, container, false)
    }

    override fun bind() = with(binding){
        adapter = MessageAdapter()
        rvMessages.adapter = adapter
        rvMessages.layoutManager = LinearLayoutManager(requireContext())

        setUp()
    }

    private fun setUp(){
        observers()
    }

    override fun listeners() = with(binding){
        btnSend.setOnClickListener {
            send()
        }
    }
    private fun send() = with(binding){
        val text = etMessage.text.toString()
        if (text.isNotEmpty()) {
            viewModel.sendMessage(text)
            etMessage.text.clear()
        }
    }
    private fun observers(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.messages.collectLatest { messages ->
                adapter.submitList(messages)
                binding.rvMessages.scrollToPosition(0)
            }
        }
    }
}