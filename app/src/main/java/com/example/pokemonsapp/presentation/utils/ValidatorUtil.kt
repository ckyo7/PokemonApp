package com.example.pokemonsapp.presentation.utils

import com.example.pokemonsapp.data.api.models.AbilityDetailResponse
import com.example.pokemonsapp.data.api.models.PokemonDetailResponse
import com.example.pokemonsapp.data.api.models.PokemonResponse
import com.example.pokemonsapp.data.api.models.ValidatedAbility
import com.example.pokemonsapp.data.api.models.ValidatedAbilityDetail
import com.example.pokemonsapp.data.api.models.ValidatedAbilityResponse
import com.example.pokemonsapp.data.api.models.ValidatedOther
import com.example.pokemonsapp.data.api.models.ValidatedPokemonDetail
import com.example.pokemonsapp.data.api.models.ValidatedPokemonModel
import com.example.pokemonsapp.data.api.models.ValidatedShowdown
import com.example.pokemonsapp.data.api.models.ValidatedSprites
import com.example.pokemonsapp.data.api.models.ValidatedType


    fun validateAbilityDetail(response: AbilityDetailResponse?): ValidatedAbilityDetail? {
        val secondEffect = response?.effecEntries?.getOrNull(1)?.effect
        return if (!secondEffect.isNullOrEmpty() && !response.name.isNullOrEmpty()) {
            ValidatedAbilityDetail(
                effect = secondEffect,
                name = response.name
            )
        } else {
            null
        }
    }

    fun getPokemonListValidated(response: PokemonResponse?): List<ValidatedPokemonModel> {
        return response?.results?.mapNotNull { model ->
            if (model != null && !model.name.isNullOrEmpty() && !model.url.isNullOrEmpty()) {
                ValidatedPokemonModel(model.url, model.name)
            } else {
                null
            }
        } ?: emptyList()
    }

    fun getValidateAndCreateValidatedPokemonDetail(response: PokemonDetailResponse?): ValidatedPokemonDetail? {
        if (response?.id != null && !response.name.isNullOrEmpty() &&
            response.height != null &&
            response.weight != null &&
            response.baseExperience != null &&
            response.sprites != null &&
            response.sprites.other != null &&
            response.sprites.other.showdown != null &&
            response.abilities != null &&
            response.abilities.all { it?.ability?.name != null && it.ability.url != null } && response.types?.all { !it.type?.name.isNullOrEmpty() } == true
        ) {
            val validatedSprites = ValidatedSprites(
                other = ValidatedOther(
                    showdown = ValidatedShowdown(
                        frontDefault = response.sprites.other.showdown!!.frontDefault!!,
                        frontShiny = response.sprites.other.showdown!!.frontShiny!!
                    )
                )
            )

            val validatedAbilities = response.abilities.map {
                ValidatedAbilityResponse(
                    ability = ValidatedAbility(
                        name = it!!.ability!!.name!!,
                        url = it.ability?.url!!
                    ),
                    isHidden = it.isHidden ?: false
                )
            }

            val validatedTypes = response.types.map {
                ValidatedType(name = it.type?.name!!)
            }

            return ValidatedPokemonDetail(
                id = response.id,
                name = response.name,
                height = response.height,
                weight = response.weight,
                baseExperience = response.baseExperience,
                sprites = validatedSprites,
                abilities = validatedAbilities,
                types = validatedTypes
            )
        }

        return null
    }
