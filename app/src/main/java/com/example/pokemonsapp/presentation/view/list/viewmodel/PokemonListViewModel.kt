package com.example.pokemonsapp.presentation.view.list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.pokemonsapp.data.api.models.ValidatedPokemonModel
import com.example.pokemonsapp.data.client.RetrofitClient
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.domain.usecases.GetPokemonListUseCase
import com.example.pokemonsapp.presentation.BaseViewModel
import com.example.pokemonsapp.presentation.GlobalConstants
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase = GetPokemonListUseCase(
        PokemonRepository(RetrofitClient.instance)
    )
) : BaseViewModel() {

    private val textFilter: MutableLiveData<String> = MutableLiveData()
    val pokemonList: MutableLiveData<List<ValidatedPokemonModel>> = MutableLiveData()
    val pokemonFilteredList = textFilter.switchMap { textFilter ->
        pokemonList.map { list ->
            if (textFilter.isEmpty()) {
                list
            } else {
                list.filter { pokemon -> pokemon.name.uppercase().contains(textFilter.uppercase()) }
            }
        }
    }

    fun obtainPokemonList() = viewModelScope.launch {
        isLoading.postValue(true)
        getPokemonListUseCase.invoke().collect { result ->
            when (result) {
                is Resource.Error -> {
                    toastErrorMessage.postValue(result.message)
                }

                is Resource.Success -> {
                    isLoading.postValue(false)
                    setInitialData(result.data)
                }
            }
        }

    }

    fun filterPokemonList(textWritten: String) = textFilter.postValue(textWritten)


    private fun setInitialData(results: List<ValidatedPokemonModel>) {
        pokemonList.postValue(results)
        textFilter.postValue(GlobalConstants.EMPTY_TEXT)
    }
}