package com.example.pokemonsapp.presentation.view.detail.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.pokemonsapp.data.api.models.PokemonDetailResponse
import com.example.pokemonsapp.data.client.RetrofitClient
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.domain.usecases.GetPokemonDetailUseCase
import com.example.pokemonsapp.presentation.BaseViewModel
import com.example.pokemonsapp.presentation.utils.PokemonUtil
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase = GetPokemonDetailUseCase(
        PokemonRepository(RetrofitClient.instance)
    )
) : BaseViewModel() {

    val pokemonDetailData: MutableLiveData<PokemonDetailResponse> = MutableLiveData()

    val typesWrapper = pokemonDetailData.map {data->
        data.types.map {
            TypeWrapper(
                it.type.name,
                PokemonUtil.getColorByType(it.type.name)
            )
        }
    }

    val heightData= pokemonDetailData.map {data->
        data.height
    }
    val weightData= pokemonDetailData.map {data->
        data.weight
    }


    data class TypeWrapper(
        val type: String,
        val typeColor: Int
    )

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
                    pokemonDetailData.postValue(result.data)
                }
            }

        }
    }
}

