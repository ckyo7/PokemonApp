package com.example.pokemonsapp.data.api.models

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("weight")
    val weight: Int? = null,
    @SerializedName("base_experience")
    val baseExperience: Int? = null,
    @SerializedName("sprites")
    val sprites: Sprites? = null,
    @SerializedName("abilities")
    val abilities: List<AbilityResponse?>? = null,
    @SerializedName("types")
    val types: List<WrappedType>? = null
)

data class Sprites(
    @SerializedName("other")
    val other: Other?
)
data class WrappedType(
    @SerializedName("type")
    val type: Type?
)

data class Other(
    @SerializedName("showdown")
    var showdown: Showdown?
)

data class Showdown(
    @SerializedName("front_default")
    var frontDefault: String?,
    @SerializedName("front_shiny")
    var frontShiny: String?
)

data class AbilityResponse(
    @SerializedName("ability")
    val ability: Ability?,
    @SerializedName("is_hidden")
    val isHidden: Boolean?
)

data class Ability(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

data class Type(
    @SerializedName("name")
    val name: String?
)


