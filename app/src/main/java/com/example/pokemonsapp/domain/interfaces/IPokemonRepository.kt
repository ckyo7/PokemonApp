package com.example.pokemonsapp.domain.interfaces

import com.example.pokemonsapp.data.api.models.PokemonResponse
import retrofit2.Response

interface IPokemonRepository {
    suspend fun getPokemonList(): Response<PokemonResponse?>?
}