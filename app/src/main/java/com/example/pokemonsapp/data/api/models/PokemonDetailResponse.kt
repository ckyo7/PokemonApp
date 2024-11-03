package com.example.pokemonsapp.data.api.models

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    val id: Int? = null,
    val name: String? = null,
    val height: Int? = null,
    val weight: Int? = null,
    @SerializedName("base_experience")
    val baseExperience: Int? = null,
    val sprites: Sprites? = null,
    val abilities: List<AbilityResponse?>? = null,
    val types: List<WrappedType>? = null
)

data class Sprites(
    val other: Other?
)
data class WrappedType(
    val type: Type?
)

data class Other(
    var showdown: Showdown?
)

data class Showdown(
    @SerializedName("front_default")
    var frontDefault: String?,
    @SerializedName("front_shiny")
    var frontShiny: String?
)

data class AbilityResponse(
    val ability: Ability?,
    @SerializedName("is_hidden")
    val isHidden: Boolean?
)

data class Ability(
    val name: String?,
    val url: String?
)

data class Type(
    val name: String?
)


