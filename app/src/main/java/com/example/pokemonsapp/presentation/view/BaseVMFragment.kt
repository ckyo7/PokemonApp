package com.example.pokemonsapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.pokemonsapp.presentation.BaseViewModel

abstract class BaseVMFragment<VM : BaseViewModel, B : ViewBinding> : Fragment() {

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    abstract fun initViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?,
        attachToParent : Boolean
    ): B


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onViewModelCreated()
        binding = initViewBinding(inflater,container,false)
        return binding.root
    }

    protected abstract fun onViewModelCreated()
}