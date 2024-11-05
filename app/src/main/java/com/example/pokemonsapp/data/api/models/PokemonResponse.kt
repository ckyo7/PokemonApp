package com.example.pokemonsapp.data.api.models

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("results")
    val results: List<PokemonModel?>?
)
