package com.example.pokemonsapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val toastErrorMessage = MutableLiveData<String>()
    open var hasLoadedData: Boolean = false

}