package com.example.pokemonsapp.presentation.utils

import com.example.pokemonsapp.data.api.models.Ability
import com.example.pokemonsapp.data.api.models.AbilityDetailResponse
import com.example.pokemonsapp.data.api.models.AbilityResponse
import com.example.pokemonsapp.data.api.models.EffectResponse
import com.example.pokemonsapp.data.api.models.Other
import com.example.pokemonsapp.data.api.models.PokemonDetailResponse
import com.example.pokemonsapp.data.api.models.PokemonModel
import com.example.pokemonsapp.data.api.models.PokemonResponse
import com.example.pokemonsapp.data.api.models.Showdown
import com.example.pokemonsapp.data.api.models.Sprites
import com.example.pokemonsapp.data.api.models.Type
import com.example.pokemonsapp.data.api.models.WrappedType

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UtilsTest {

    @Test
    fun `validateAbilityDetail returns ValidatedAbilityDetail when second effect exists`() {
        val response = AbilityDetailResponse(
            effecEntries = listOf(
                EffectResponse(effect = "First effect"),
                EffectResponse(effect = "Second effect")
            ),
            name = "Ability Name"
        )

        val result = validateAbilityDetail(response)

        assertEquals("Second effect", result?.effect)
        assertEquals("Ability Name", result?.name)
    }

    @Test
    fun `validateAbilityDetail returns null when second effect is missing`() {
        val response = AbilityDetailResponse(
            effecEntries = listOf(
                EffectResponse(effect = "First effect")
            ),
            name = "Ability Name"
        )

        val result = validateAbilityDetail(response)

        assertNull(result)
    }

    @Test
    fun `getPokemonListValidated returns a list of ValidatedPokemonModel`() {
        val response = PokemonResponse(
            results = listOf(
                PokemonModel(name = "Pikachu", url = "http://example.com/pikachu"),
                PokemonModel(name = "Bulbasaur", url = "http://example.com/bulbasaur"),
                PokemonModel(name = null, url = "http://example.com/invalid"),
                PokemonModel(name = "Charmander", url = "")
            )
        )

        val result = getPokemonListValidated(response)

        assertEquals(2, result.size)
        assertEquals("Pikachu", result[0].name)
        assertEquals("Bulbasaur", result[1].name)
    }

    @Test
    fun `getPokemonListValidated returns an empty list when all models are invalid`() {
        val response = PokemonResponse(results = listOf())

        val result = getPokemonListValidated(response)

        assertEquals(0, result.size)
    }

    @Test
    fun `getValidateAndCreateValidatedPokemonDetail returns ValidatedPokemonDetail when valid data is provided`() {
        val response = PokemonDetailResponse(
            id = 1,
            name = "Pikachu",
            height = 10,
            weight = 30,
            baseExperience = 112,
            sprites = Sprites(other = Other(showdown = Showdown("frontDefaultUrl", "frontShinyUrl"))),
            abilities = listOf(AbilityResponse(ability = Ability(name = "Static", url = "http://example.com/static"), isHidden = false)),
            types = listOf(WrappedType(Type(name = "Electric")))
        )

        val result = getValidateAndCreateValidatedPokemonDetail(response)

        assertEquals(1, result?.id)
        assertEquals("Pikachu", result?.name)
        assertEquals(10, result?.height)
        assertEquals(30, result?.weight)
        assertEquals(112, result?.baseExperience)
        assertEquals("frontDefaultUrl", result?.sprites?.other?.showdown?.frontDefault)
        assertEquals("frontShinyUrl", result?.sprites?.other?.showdown?.frontShiny)
        assertEquals(1, result?.abilities?.size)
        assertEquals("Static", result?.abilities?.first()?.ability?.name)
        assertEquals("Electric", result?.types?.first()?.name)
    }

    @Test
    fun `getValidateAndCreateValidatedPokemonDetail returns null when required data is missing`() {
        val response = PokemonDetailResponse(
            id = null,  // missing id
            name = "Pikachu",
            height = 10,
            weight = 30,
            baseExperience = 112,
            sprites = Sprites(other = Other(showdown = Showdown("frontDefaultUrl", "frontShinyUrl"))),
            abilities = listOf(AbilityResponse(ability = Ability(name = "Static", url = "http://example.com/static"), isHidden = false)),
            types = listOf(WrappedType(Type(name = "Electric")))
        )

        val result = getValidateAndCreateValidatedPokemonDetail(response)

        assertNull(result)
    }
}