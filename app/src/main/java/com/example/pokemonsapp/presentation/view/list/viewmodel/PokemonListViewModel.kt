package com.example.pokemonsapp.presentation.view.list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.pokemonsapp.data.api.models.PokemonModel
import com.example.pokemonsapp.data.client.RetrofitClient
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.domain.usecases.GetPokemonListUseCase
import com.example.pokemonsapp.presentation.BaseViewModel
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase = GetPokemonListUseCase(
        PokemonRepository(RetrofitClient.instance)
    )
) : BaseViewModel() {

    val pokemonList: MutableLiveData<List<PokemonModel>> = MutableLiveData()
    val textFilter: MutableLiveData<String> = MutableLiveData()
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
        getPokemonListUseCase.invoke().collect { result ->
            when (result) {
                is Resource.Error -> {
                    setInitialData(listOf(PokemonModel("HOLA", "Paco")))

                }

                is Resource.Loading -> {
                    isLoading.postValue(true)
                }

                is Resource.Success -> {
                    isLoading.postValue(false)
                    setInitialData(result.data.results)
                }
            }
        }

    }

    fun filterPokemonList(textWritten: String) = textFilter.postValue(textWritten)


    private fun setInitialData(results: List<PokemonModel>) {
        pokemonList.postValue(results)
        textFilter.postValue("")
    }
}