package com.example.pokemonsapp.data.api.models


data class ValidatedPokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
    val sprites: ValidatedSprites,
    val abilities: List<ValidatedAbilityResponse>,
    val types: List<ValidatedType>
)

data class ValidatedSprites(
    val other: ValidatedOther
)

data class ValidatedOther(
    var showdown: ValidatedShowdown
)

data class ValidatedShowdown(
    var frontDefault: String,
    var frontShiny: String
)

data class ValidatedAbilityResponse(
    val ability: ValidatedAbility, // Reference to the ability data
    val isHidden: Boolean // Whether the ability is hidden
)

data class ValidatedAbility(
    val name: String,
    val url: String
)

data class ValidatedType(
    val name: String
)