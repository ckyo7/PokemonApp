package com.example.pokemonsapp.data.api.services

import com.example.pokemonsapp.data.api.models.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemonList(): Response<PokemonResponse?>
}