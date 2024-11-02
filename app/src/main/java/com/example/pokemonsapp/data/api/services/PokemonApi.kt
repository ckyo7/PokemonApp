package com.example.pokemonsapp.data.api.services

import com.example.pokemonsapp.data.api.models.PokemonDetailResponse
import com.example.pokemonsapp.data.api.models.PokemonResponse
import com.example.pokemonsapp.domain.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("pokemon?limit=151")
    suspend fun getPokemonList(): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name : String): Response<PokemonDetailResponse>
}