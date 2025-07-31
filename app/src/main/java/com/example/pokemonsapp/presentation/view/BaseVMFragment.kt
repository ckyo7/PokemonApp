package com.example.pokemonsapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.pokemonsapp.presentation.BaseViewModel

abstract class BaseVMFragment<VM : BaseViewModel, B : ViewBinding> : BaseFragment<B>() {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewModelCreated()
    }

    protected abstract fun onViewModelCreated()
}