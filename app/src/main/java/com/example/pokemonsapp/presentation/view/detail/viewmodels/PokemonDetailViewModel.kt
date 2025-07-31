package com.example.pokemonsapp.presentation.view.detail.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.pokemonsapp.data.api.models.ValidatedAbilityDetail
import com.example.pokemonsapp.data.api.models.ValidatedPokemonDetail
import com.example.pokemonsapp.data.client.RetrofitClient
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.domain.usecases.GetAbilityDetailUseCase
import com.example.pokemonsapp.domain.usecases.GetPokemonDetailUseCase
import com.example.pokemonsapp.presentation.BaseViewModel
import com.example.pokemonsapp.presentation.GlobalConstants
import com.example.pokemonsapp.presentation.utils.getColorByType
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase = GetPokemonDetailUseCase(
        PokemonRepository(RetrofitClient.instance)
    ),
    private val getAbilityDetailUseCase: GetAbilityDetailUseCase = GetAbilityDetailUseCase(
        PokemonRepository(RetrofitClient.instance)
    ),
) : BaseViewModel() {

    private val pokemonDetailData: MutableLiveData<ValidatedPokemonDetail> = MutableLiveData()
    var abilityDetailData: MutableLiveData<ValidatedAbilityDetail> = MutableLiveData()
    val abilityClicked: MutableLiveData<String> = MutableLiveData()

    val frontSprite = pokemonDetailData.map {
        it.sprites.other.showdown.frontDefault
    }

    val nameWrapper = pokemonDetailData.map { detail ->
        NameWrapper(
            detail.name.replaceFirstChar { it.uppercase() },
            detail.id.toString(),
            detail.baseExperience.toString()
        )
    }

    val typesWrapper = pokemonDetailData.map { data ->
        data.types.map { type ->
            TypeWrapper(
                type.name.replaceFirstChar { it.uppercase() },
                getColorByType(type.name)
            )
        }
    }

    val abilityList = pokemonDetailData.map {
        it.abilities.map { abilityWrapped ->
            AbilityWrapper(
                abilityWrapped.ability.name,
                abilityWrapped.ability.name.replace(
                    GlobalConstants.HYPHEN_CHAR,
                    GlobalConstants.EMPTY_TEXT_SPACE
                ).replaceFirstChar { char -> char.uppercase() }
            )
        }
    }

    val heightData = pokemonDetailData.map { data ->
        data.height * GlobalConstants.CONVERSION_RATE
    }
    val weightData = pokemonDetailData.map { data ->
        data.weight * GlobalConstants.CONVERSION_RATE
    }

    fun obtainPokemonDetail(name: String) {
        if (!pokemonDetailData.isInitialized) {
            isLoading.postValue(true)
            viewModelScope.launch {
                getPokemonDetailUseCase.invoke(name).collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            toastErrorMessage.postValue(result.message)
                        }

                        is Resource.Success -> {
                            pokemonDetailData.postValue(result.data)
                            isLoading.postValue(false)
                            hasLoadedData = true
                        }
                    }

                }
            }
        }
    }

    fun obtainAbilityDetail(name: String) = viewModelScope.launch {
        isLoading.postValue(true)
        getAbilityDetailUseCase.invoke(name).collect { result ->
            when (result) {
                is Resource.Error -> {
                    toastErrorMessage.postValue(result.message)
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
            val id: String,
            val baseExperience: String
        )

        data class AbilityWrapper(
            val originalName: String,
            val formattedName: String
        )
    }
}

