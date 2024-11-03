package com.example.pokemonsapp.domain.usecases

import com.example.pokemonsapp.data.api.models.AbilityDetailResponse
import com.example.pokemonsapp.data.api.models.EffectResponse
import com.example.pokemonsapp.data.api.models.ValidatedAbilityDetail
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
import org.mockito.Mockito
import retrofit2.Response
import java.io.IOException


@ExperimentalCoroutinesApi
class GetAbilityDetailUseCaseTest {
    @MockK
    private lateinit var repository: PokemonRepository
    private lateinit var useCase: GetAbilityDetailUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        useCase = GetAbilityDetailUseCase(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `when repository returns empty list then emit Error`() = runBlocking {
        coEvery { repository.getAbilityDetail(Mockito.anyString()) } returns Response.success(
            AbilityDetailResponse()
        )

        useCase.invoke(Mockito.anyString()).collect {
            Assert.assertEquals(Resource.Error("Wrong data from Service"), it)
        }

        coVerify(exactly = 1) { repository.getAbilityDetail(Mockito.anyString()) }
    }

    @Test
    fun `when repository returns list with just wrong models then emit Error `() = runBlocking {
        coEvery { repository.getAbilityDetail(Mockito.anyString()) } returns Response.success(
            AbilityDetailResponse(
                effecEntries = listOf(
                    EffectResponse(effect = "First effect description"), // Puede ser ignorado
                    EffectResponse(effect = null)
                ),
                name = "Sample Ability"
            )
        )

        useCase.invoke(Mockito.anyString()).collect {
            Assert.assertEquals(Resource.Error("Wrong data from Service"), it)
        }

        coVerify(exactly = 1) { repository.getAbilityDetail(Mockito.anyString()) }
    }

    @Test
    fun `when repository returns successful response then emit Success`() = runBlocking {
        coEvery { repository.getAbilityDetail(Mockito.anyString()) } returns Response.success(
            AbilityDetailResponse(
                effecEntries = listOf(
                    EffectResponse(effect = "First effect description"), // Puede ser ignorado
                    EffectResponse(effect = "First effect description")
                ),
                name = "Sample Ability"
            )
        )

        useCase.invoke(Mockito.anyString()).collect {
            Assert.assertEquals(
                Resource.Success(
                    ValidatedAbilityDetail(
                        "First effect description",
                        "Sample Ability"
                    )
                ), it
            )
        }

        coVerify(exactly = 1) { repository.getAbilityDetail(Mockito.anyString()) }
    }

    @Test
    fun `when repository returns error response then emit Success`() = runBlocking {
        coEvery { repository.getAbilityDetail(Mockito.anyString()) } returns Response.error(
            418,
            ResponseBody.create(null, "error")
        )

        useCase.invoke(Mockito.anyString()).collect {
            Assert.assertEquals(
                Resource.Error("Error 418: Response.error()"),
                it
            )
        }

        coVerify(exactly = 1) { repository.getAbilityDetail(Mockito.anyString()) }
    }

    @Test
    fun `when repository throws IOException, should emit error`() = runBlocking {
        // Arrange
        coEvery { repository.getAbilityDetail(Mockito.anyString()) } throws IOException("error")

        useCase.invoke(Mockito.anyString()).collect {
            Assert.assertEquals(
                Resource.Error("Api error : error"),
                it
            )
        }

        coVerify(exactly = 1) { repository.getAbilityDetail(Mockito.anyString()) }
    }

    @Test
    fun `when repository throws Exception, should emit error`() = runBlocking {
        // Arrange
        coEvery { repository.getAbilityDetail(Mockito.anyString()) } throws Exception("General error")

        useCase.invoke(Mockito.anyString()).collect {
            Assert.assertEquals(
                Resource.Error("Error: General error"),
                it
            )
        }

        coVerify(exactly = 1) { repository.getAbilityDetail(Mockito.anyString()) }
    }
}