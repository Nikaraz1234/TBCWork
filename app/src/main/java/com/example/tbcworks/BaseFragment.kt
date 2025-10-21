package com.example.tbcworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.example.tbcworks.viewModel.UserViewModel

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    protected val userViewModel: UserViewModel by activityViewModels()

    // Each fragment must provide its own inflate method
    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    open fun listeners() {}
    open fun bind() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}