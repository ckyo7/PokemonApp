package com.example.pokemonsapp.data.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ValidatedAbilityDetail(
    val effect: String,
    val name: String
) : Parcelable
