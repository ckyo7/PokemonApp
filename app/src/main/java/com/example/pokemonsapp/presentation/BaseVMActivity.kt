package com.example.pokemonsapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.pokemonsapp.R

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

        viewModel.toastErrorMessage.observe(this) {
            Toast.makeText(this, getString(R.string.service_error_message,it), Toast.LENGTH_SHORT).show()
            viewModel.isLoading.postValue(false)
        }
    }

    protected abstract fun onViewBindingCreated()

    protected abstract fun onViewModelCreated()

}