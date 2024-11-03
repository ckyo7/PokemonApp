package com.example.pokemonsapp.data.api.models

import com.google.gson.annotations.SerializedName

data class AbilityDetailResponse(
    @SerializedName("effect_entries")
    val effecEntries: List<EffectResponse>,
    val name: String
)

data class EffectResponse(
    val effect: String
)