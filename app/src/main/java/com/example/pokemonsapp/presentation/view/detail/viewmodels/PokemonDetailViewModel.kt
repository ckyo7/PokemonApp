package com.example.pokemonsapp.presentation.view.detail.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.pokemonsapp.data.api.models.AbilityDetailResponse
import com.example.pokemonsapp.data.api.models.PokemonDetailResponse
import com.example.pokemonsapp.data.client.RetrofitClient
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.domain.usecases.GetAbilityDetailUseCase
import com.example.pokemonsapp.domain.usecases.GetPokemonDetailUseCase
import com.example.pokemonsapp.presentation.BaseViewModel
import com.example.pokemonsapp.presentation.GlobalConstants
import com.example.pokemonsapp.presentation.utils.PokemonUtil
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase = GetPokemonDetailUseCase(
        PokemonRepository(RetrofitClient.instance)
    ),
    private val getAbilityDetailUseCase: GetAbilityDetailUseCase = GetAbilityDetailUseCase(
        PokemonRepository(RetrofitClient.instance)
    ),
) : BaseViewModel() {

    private val pokemonDetailData: MutableLiveData<PokemonDetailResponse> = MutableLiveData()
    val abilityDetailData: MutableLiveData<AbilityDetailResponse> = MutableLiveData()
    val abilityClicked: MutableLiveData<String> = MutableLiveData()

    val frontSprite = pokemonDetailData.map {
        it.sprites.other.showdown?.frontDefault
    }

    val nameWrapper = pokemonDetailData.map {
        NameWrapper(
            it.name.replaceFirstChar { char -> char.uppercase() },
            it.id.toString()
        )
    }

    val typesWrapper = pokemonDetailData.map { data ->
        data.types.map {
            TypeWrapper(
                it.type.name.replaceFirstChar { char -> char.uppercase() },
                PokemonUtil.getColorByType(it.type.name)
            )
        }
    }

    val abilityList = pokemonDetailData.map {
        it.abilities.map { ability ->
            AbilityWrapper(
                ability.ability.name,
                ability.ability.name.replace(
                    GlobalConstants.HYPHEN_CHAR,
                    GlobalConstants.EMPTY_TEXT_SPACE
                )
                    .replaceFirstChar { char -> char.uppercase() }
            )
        }
    }

    val heightData = pokemonDetailData.map { data ->
        data.height
    }
    val weightData = pokemonDetailData.map { data ->
        data.weight
    }

    fun obtainPokemonList(name: String) {
        if (!pokemonDetailData.isInitialized) {
            viewModelScope.launch {
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
                            isLoading.postValue(false)
                        }
                    }

                }
            }
        }
    }

    fun obtainAbilityDetail(name: String) = viewModelScope.launch {
        getAbilityDetailUseCase.invoke(name).collect { result ->
            when (result) {
                is Resource.Error -> {
                    //setInitialData(listOf(PokemonModel("HOLA", "Paco")))
                }

                is Resource.Loading -> {
                    isLoading.postValue(true)
                }

                is Resource.Success -> {
                    abilityDetailData.postValue(result.data)
                    isLoading.postValue(false)
                }
            }

        }
    }

    companion object {
        data class TypeWrapper(
            val type: String,
            val typeColor: Int
        )

        data class NameWrapper(
            val name: String,
            val id: String
        )

        data class AbilityWrapper(
            val originalName: String,
            val formattedName: String
        )

    }
}

