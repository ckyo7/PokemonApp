package com.example.pokemonsapp.data.repository

import com.example.pokemonsapp.data.api.models.PokemonResponse
import com.example.pokemonsapp.data.api.services.PokemonService
import com.example.pokemonsapp.domain.interfaces.IPokemonRepository
import retrofit2.Response
import retrofit2.Retrofit

class PokemonRepository(private val retrofit: Retrofit) : IPokemonRepository {
    override suspend fun getPokemonList(): Response<PokemonResponse?> {
        return retrofit.create(PokemonService::class.java).getPokemonList()
    }
}