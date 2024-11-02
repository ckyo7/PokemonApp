package com.example.pokemonsapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseVMActivity<VM : BaseViewModel, B : ViewBinding> :
    AppCompatActivity() {

    val binding: B by lazy { initViewBinding() }
    abstract val viewModel: VM

    abstract fun initViewBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onViewBindingCreated()
        onViewModelCreated()
    }

    protected abstract fun onViewBindingCreated()

    protected abstract fun onViewModelCreated()

}