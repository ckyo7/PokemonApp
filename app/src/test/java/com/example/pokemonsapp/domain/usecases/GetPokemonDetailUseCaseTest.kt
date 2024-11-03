package com.example.pokemonsapp.domain.usecases

import com.example.pokemonsapp.data.api.models.Ability
import com.example.pokemonsapp.data.api.models.AbilityResponse
import com.example.pokemonsapp.data.api.models.Other
import com.example.pokemonsapp.data.api.models.PokemonDetailResponse
import com.example.pokemonsapp.data.api.models.Showdown
import com.example.pokemonsapp.data.api.models.Sprites
import com.example.pokemonsapp.data.api.models.Type
import com.example.pokemonsapp.data.api.models.WrappedType
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.presentation.utils.getValidateAndCreateValidatedPokemonDetail
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class GetPokemonDetailUseCaseTest {
    @MockK
    private lateinit var repository: PokemonRepository
    private lateinit var useCase: GetPokemonDetailUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        useCase = GetPokemonDetailUseCase(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `when repository returns wrong data then emit Error`() = runBlocking {
        coEvery { repository.getPokemonDetail(Mockito.anyString()) } returns Response.success(
            PokemonDetailResponse()
        )

        useCase.invoke(Mockito.anyString()).collect {
            Assert.assertEquals(Resource.Error("Wrong data from Service"), it)
        }

        coVerify(exactly = 1) { repository.getPokemonDetail(Mockito.anyString()) }
    }

    @Test
    fun `when repository returns right data then emit Error`() = runBlocking {
        val rightPokemon = PokemonDetailResponse(
            id = 1,
            name = "Bulbasaur",
            height = 7,
            weight = 69,
            baseExperience = 64,
            sprites = Sprites(
                other = Other(
                    showdown = Showdown(
                        frontDefault = "https://example.com/front_default.png",
                        frontShiny = "https://example.com/front_shiny.png"
                    )
                )
            ),
            abilities = listOf(
                AbilityResponse(
                    ability = Ability(
                        name = "Overgrow",
                        url = "https://pokeapi.co/api/v2/ability/65/"
                    ),
                    isHidden = false
                ),
                AbilityResponse(
                    ability = Ability(
                        name = "Chlorophyll",
                        url = "https://pokeapi.co/api/v2/ability/34/"
                    ),
                    isHidden = true
                )
            ),
            types = listOf(
                WrappedType(
                    Type(name = "Grass")
                ),
                WrappedType(
                    Type(name = "Poison")
                )
            )
        )
        val validatedRightData =
            getValidateAndCreateValidatedPokemonDetail(rightPokemon)

        coEvery { repository.getPokemonDetail(Mockito.anyString()) } returns Response.success(
            rightPokemon
        )

        useCase.invoke(Mockito.anyString()).collect {
            Assert.assertEquals(Resource.Success(validatedRightData), it)
        }

        coVerify(exactly = 1) { repository.getPokemonDetail(Mockito.anyString()) }
    }


    @Test
    fun `when repository returns error response then emit Success`() = runBlocking {
        coEvery { repository.getPokemonDetail(Mockito.anyString()) } returns Response.error(
            418,
            ResponseBody.create(null, "error")
        )

        useCase.invoke(Mockito.anyString()).collect {
            Assert.assertEquals(
                Resource.Error("Error 418: Response.error()"),
                it
            )
        }

        coVerify(exactly = 1) { repository.getPokemonDetail(Mockito.anyString()) }
    }

    @Test
    fun `when repository throws IOException, should emit error`() = runBlocking {
        // Arrange
        coEvery { repository.getPokemonDetail(Mockito.anyString()) } throws IOException("error")

        useCase.invoke(Mockito.anyString()).collect {
            Assert.assertEquals(
                Resource.Error("Api error : error"),
                it
            )
        }

        coVerify(exactly = 1) { repository.getPokemonDetail(Mockito.anyString()) }
    }

    @Test
    fun `when repository throws Exception, should emit error`() = runBlocking {
        // Arrange
        coEvery { repository.getPokemonDetail(Mockito.anyString()) } throws Exception("General error")

        useCase.invoke(Mockito.anyString()).collect {
            Assert.assertEquals(
                Resource.Error("Error: General error"),
                it
            )
        }

        coVerify(exactly = 1) { repository.getPokemonDetail(Mockito.anyString()) }
    }


}