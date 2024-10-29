package com.example.pokemonsapp.presentation.view.pokemonlist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonsapp.data.api.models.PokemonModel
import com.example.pokemonsapp.data.api.models.PokemonResponse
import com.example.pokemonsapp.data.client.RetrofitClient
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.usecases.GetPokemonListUseCase
import kotlinx.coroutines.launch

class ListadoPokemonViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase = GetPokemonListUseCase(
        PokemonRepository(RetrofitClient.instance)
    )
) : ViewModel() {

    val pokemonList: MutableLiveData<List<PokemonModel>> = MutableLiveData()

    fun obtainPokemonList() {
        viewModelScope.launch {
            val result = getPokemonListUseCase.invoke()

            if (result.isSuccessful && result.body() != null) {
                pokemonList.postValue(result.body()!!.results)
            } else {
                when (result.code()) {

                }
            }
        }
    }
}