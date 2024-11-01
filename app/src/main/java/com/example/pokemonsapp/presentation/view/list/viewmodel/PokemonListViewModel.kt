package com.example.pokemonsapp.presentation.view.list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.pokemonsapp.data.api.models.PokemonModel
import com.example.pokemonsapp.data.client.RetrofitClient
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.usecases.GetPokemonListUseCase
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase = GetPokemonListUseCase(
        PokemonRepository(RetrofitClient.instance)
    )
) : ViewModel() {

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

    fun obtainPokemonList() {
        viewModelScope.launch {
            val result = getPokemonListUseCase.invoke()

            if (result.isSuccessful && result.body() != null) {
                result.body()?.results?.let {
                    setInitialData(it)
                } ?: {
                    //Handle null data error
                }
            } else {
                when (result.code()) {

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