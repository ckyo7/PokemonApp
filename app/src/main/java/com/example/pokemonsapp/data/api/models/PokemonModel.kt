package com.example.pokemonsapp.data.api.models

import com.google.gson.annotations.SerializedName

data class PokemonModel(
    @SerializedName("url")
    val url: String?,
    @SerializedName("name")
    val name: String?
)