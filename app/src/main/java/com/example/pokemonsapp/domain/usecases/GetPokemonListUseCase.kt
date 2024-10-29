package com.example.pokemonsapp.domain.usecases

import com.example.pokemonsapp.data.repository.PokemonRepository

class GetPokemonListUseCase(
    private val repository: PokemonRepository
) {
    suspend fun invoke() = repository.getPokemonList()
}