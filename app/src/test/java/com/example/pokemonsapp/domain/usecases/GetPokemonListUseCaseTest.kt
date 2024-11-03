package com.example.pokemonsapp.domain.usecases

import com.example.pokemonsapp.data.api.models.PokemonModel
import com.example.pokemonsapp.data.api.models.PokemonResponse
import com.example.pokemonsapp.data.api.models.ValidatedPokemonModel
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.Resource
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
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class GetPokemonListUseCaseTest {
    @MockK
    private lateinit var repository: PokemonRepository
    private lateinit var useCase: GetPokemonListUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        useCase = GetPokemonListUseCase(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `when repository returns empty list then emit Error`() = runBlocking {
        coEvery { repository.getPokemonList() } returns Response.success(PokemonResponse(emptyList()))

        useCase.invoke().collect {
            Assert.assertEquals(Resource.Error("Wrong data from Service"), it)
        }

        coVerify(exactly = 1) { repository.getPokemonList() }
    }

    @Test
    fun `when repository returns list with just wrong models then emit Error `() = runBlocking {
        coEvery { repository.getPokemonList() } returns Response.success(
            PokemonResponse(
                listOf(
                    PokemonModel("", "a"),
                    PokemonModel(null, "a"),
                    PokemonModel("a", null),
                    PokemonModel("aa", ""),
                    null
                )
            )
        )

        useCase.invoke().collect {
            Assert.assertEquals(Resource.Error("Wrong data from Service"), it)
        }

        coVerify(exactly = 1) { repository.getPokemonList() }
    }

    @Test
    fun `when repository returns null model then emit Error `() = runBlocking {
        coEvery { repository.getPokemonList() } returns Response.success(null)

        useCase.invoke().collect {
            Assert.assertEquals(Resource.Error("Wrong data from Service"), it)
        }

        coVerify(exactly = 1) { repository.getPokemonList() }
    }

    @Test
    fun `when repository returns successful response then emit Success`() = runBlocking {
        val mockPokemonList = listOf(PokemonModel("URL", "Pikachu"))
        coEvery { repository.getPokemonList() } returns Response.success(
            PokemonResponse(
                mockPokemonList
            )
        )

        useCase.invoke().collect {
            Assert.assertEquals(
                Resource.Success(listOf(ValidatedPokemonModel("URL", "Pikachu"))),
                it
            )
        }

        coVerify(exactly = 1) { repository.getPokemonList() }
    }

    @Test
    fun `when repository returns error response then emit Success`() = runBlocking {
        coEvery { repository.getPokemonList() } returns Response.error(
            418,
            ResponseBody.create(null, "error")
        )

        useCase.invoke().collect {
            Assert.assertEquals(
                Resource.Error("Error 418: Response.error()"),
                it
            )
        }

        coVerify(exactly = 1) { repository.getPokemonList() }
    }

    @Test
    fun `when repository throws IOException, should emit error`() = runBlocking {
        // Arrange
        coEvery { repository.getPokemonList() } throws IOException("error")

        useCase.invoke().collect {
            Assert.assertEquals(
                Resource.Error("Api error : error"),
                it
            )
        }

        coVerify(exactly = 1) { repository.getPokemonList() }
    }

    @Test
    fun `when repository throws Exception, should emit error`() = runBlocking {
        // Arrange
        coEvery { repository.getPokemonList() } throws Exception("General error")

        useCase.invoke().collect {
            Assert.assertEquals(
                Resource.Error("Error: General error"),
                it
            )
        }

        coVerify(exactly = 1) { repository.getPokemonList() }
    }
}