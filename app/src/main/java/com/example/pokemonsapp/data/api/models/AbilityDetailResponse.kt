package com.example.pokemonsapp.data.api.models

import com.google.gson.annotations.SerializedName

data class AbilityDetailResponse(
    @SerializedName("effect_entries")
    val effecEntries: List<EffectResponse>? = null,
    val name: String? = null
)

data class EffectResponse(
    val effect: String?
)