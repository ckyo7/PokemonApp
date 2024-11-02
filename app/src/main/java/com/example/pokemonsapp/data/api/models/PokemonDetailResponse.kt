package com.example.pokemonsapp.data.api.models

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
    val sprites: Sprites,
    val abilities: List<Ability>,
    val types: List<Type>,
    val stats: List<Stat>,
    val moves: List<Move>,
    val heldItems: List<HeldItem>,
    // Add other fields as needed based on the response structure
)

data class Sprites(
    @SerializedName("front_default")
    val front: String,
    @SerializedName("back_default")
    val back: String,
    val other: Other
)

data class Other(
    @SerializedName("dream_world")
    var home: HomeArtwork?,
    @SerializedName("official-artwork")
    var officialArtwork: OfficialArtwork?,
    var showdown: Showdown?
)

data class Showdown(
    @SerializedName("back_default")
    var backDefault: String?,
    @SerializedName("back_shiny")
    var backShiny: String?,
    @SerializedName("front_default")
    var frontDefault: String?,
    @SerializedName("front_shiny")
    var frontShiny: String?
)
data class HomeArtwork(
    val front_default: String // URL of the official artwork sprite
)
data class OfficialArtwork(
    val front_default: String // URL of the official artwork sprite
)

data class Ability(
    val ability: NamedAPIResource, // Reference to the ability data
    val isHidden: Boolean // Whether the ability is hidden
)

data class NamedAPIResource(
    val name: String, // Name of the referenced resource
    val url: String // URL of the referenced resource
)

data class Type(
    val type: NamedAPIResource // Reference to the type data
)

data class Stat(
    val stat: NamedAPIResource, // Reference to the stat data
    val baseStat: Int // The base value of the stat
)

data class Move(
    val move: NamedAPIResource, // Reference to the move data
)

data class HeldItem(
    val item: NamedAPIResource, // Reference to the held item data
    val versionDetails: List<VersionDetail> // Version-specific details about the held item
)

data class VersionDetail(
    val version: NamedAPIResource, // Reference to the version data
    val rarity: Int // Rarity of the held item in that version
)

