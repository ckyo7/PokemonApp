package com.example.pokemonsapp.presentation.view.detail.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.pokemonsapp.data.client.RetrofitClient
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.domain.usecases.GetPokemonDetailUseCase
import com.example.pokemonsapp.presentation.BaseViewModel
import kotlinx.coroutines.launch

class PokemonDetailFragmentViewModel (
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase = GetPokemonDetailUseCase(
        PokemonRepository(RetrofitClient.instance)
    )
) : BaseViewModel() {

    fun obtainPokemonList(name: String) = viewModelScope.launch {
        getPokemonDetailUseCase.invoke(name).collect { result ->
            when (result) {
                is Resource.Error -> {
                    //setInitialData(listOf(PokemonModel("HOLA", "Paco")))
                }

                is Resource.Loading -> {
                    isLoading.postValue(true)
                }

                is Resource.Success -> {
                    result.data
                }
            }

        }
    }
}

