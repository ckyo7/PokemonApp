package com.example.pokemonsapp.domain.interfaces

import com.example.pokemonsapp.data.api.models.AbilityDetailResponse
import com.example.pokemonsapp.data.api.models.PokemonDetailResponse
import com.example.pokemonsapp.data.api.models.PokemonResponse
import retrofit2.Response

interface IPokemonRepository {
    suspend fun getPokemonList(): Response<PokemonResponse>
    suspend fun getPokemonDetail(name: String): Response<PokemonDetailResponse>
    suspend fun getAbilityDetail(name: String): Response<AbilityDetailResponse>
}