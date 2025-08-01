package com.example.pokemonsapp.presentation.view.detail.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokemonsapp.data.api.models.ValidatedAbility
import com.example.pokemonsapp.data.api.models.ValidatedAbilityDetail
import com.example.pokemonsapp.data.api.models.ValidatedAbilityResponse
import com.example.pokemonsapp.data.api.models.ValidatedOther
import com.example.pokemonsapp.data.api.models.ValidatedPokemonDetail
import com.example.pokemonsapp.data.api.models.ValidatedShowdown
import com.example.pokemonsapp.data.api.models.ValidatedSprites
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.domain.usecases.GetAbilityDetailUseCase
import com.example.pokemonsapp.domain.usecases.GetPokemonDetailUseCase
import com.example.pokemonsapp.getOrAwaitValue
import com.example.pokemonsapp.presentation.GlobalConstants
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class PokemonDetailViewModelTest {
    private lateinit var viewModel: PokemonDetailViewModel

    @MockK
    private lateinit var casoUso1: GetPokemonDetailUseCase

    @MockK
    private lateinit var casoUso2: GetAbilityDetailUseCase

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {

        MockKAnnotations.init(this)
        viewModel = PokemonDetailViewModel(casoUso1, casoUso2)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when obtain pokemon detail and try again with already init data`() = runTest {
        val validatedDetail = ValidatedPokemonDetail(
            id = 1,
            name = "Pikachu",
            height = 4,
            weight = 60,
            baseExperience = 112,
            sprites = ValidatedSprites(
                other = ValidatedOther(
                    showdown = ValidatedShowdown(
                        frontDefault = "https://example.com/front_default.png",
                        frontShiny = "https://example.com/front_shiny.png"
                    )
                )
            ),
            abilities = listOf(
                ValidatedAbilityResponse(
                    ability = ValidatedAbility(
                        name = "Static",
                        url = "https://pokeapi.co/api/v2/ability/9/"
                    ),
                    isHidden = false
                ),
                ValidatedAbilityResponse(
                    ability = ValidatedAbility(
                        name = "Lightning Rod",
                        url = "https://pokeapi.co/api/v2/ability/31/"
                    ),
                    isHidden = true
                )
            ),
            types = listOf()
        )

        coEvery { casoUso1.invoke(Mockito.anyString()) } returns flowOf(
            Resource.Success(
                validatedDetail
            )
        )

        viewModel.obtainPokemonDetail(Mockito.anyString())

        assertEquals(
            validatedDetail.sprites.other.showdown.frontDefault,
            viewModel.frontSprite.getOrAwaitValue()
        )

        assertEquals(
            validatedDetail.height * GlobalConstants.CONVERSION_HEIGHT_RATE,
            viewModel.heightData.getOrAwaitValue(),
            0.0
        )

        assertEquals(
            validatedDetail.weight * GlobalConstants.CONVERSION_HEIGHT_RATE,
            viewModel.weightData.getOrAwaitValue(),
            0.0
        )

        assertNotNull(
            viewModel.typesWrapper.getOrAwaitValue()
        )

        assertEquals(
            validatedDetail.baseExperience.toString(),
            viewModel.nameWrapper.getOrAwaitValue().baseExperience
        )

        assertEquals(
            validatedDetail.name,
            viewModel.nameWrapper.getOrAwaitValue().name
        )

        assertEquals(
            validatedDetail.id.toString(),
            viewModel.nameWrapper.getOrAwaitValue().id
        )

        assertEquals(
            listOf(PokemonDetailViewModel.Companion.AbilityWrapper(
                validatedDetail.abilities[0].ability.name,
                validatedDetail.abilities[0].ability.name.replace(
                    GlobalConstants.HYPHEN_CHAR,
                    GlobalConstants.EMPTY_TEXT_SPACE
                ).replaceFirstChar { char -> char.uppercase() }

            ),
                PokemonDetailViewModel.Companion.AbilityWrapper(
                    validatedDetail.abilities[1].ability.name,
                    validatedDetail.abilities[1].ability.name.replace(
                        GlobalConstants.HYPHEN_CHAR,
                        GlobalConstants.EMPTY_TEXT_SPACE
                    ).replaceFirstChar { char -> char.uppercase() }

                )),
            viewModel.abilityList.getOrAwaitValue()
        )

        assertFalse(viewModel.isLoading.getOrAwaitValue())

        viewModel.obtainPokemonDetail(Mockito.anyString())
    }

    @Test
    fun `when obtain pokemon detail with empty name`() = runTest {
        val validatedDetail = ValidatedPokemonDetail(
            id = 1,
            name = "",
            height = 4,
            weight = 60,
            baseExperience = 112,
            sprites = ValidatedSprites(
                other = ValidatedOther(
                    showdown = ValidatedShowdown(
                        frontDefault = "https://example.com/front_default.png",
                        frontShiny = "https://example.com/front_shiny.png"
                    )
                )
            ),
            abilities = listOf(
                ValidatedAbilityResponse(
                    ability = ValidatedAbility(
                        name = "Static",
                        url = "https://pokeapi.co/api/v2/ability/9/"
                    ),
                    isHidden = false
                ),
                ValidatedAbilityResponse(
                    ability = ValidatedAbility(
                        name = "Lightning Rod",
                        url = "https://pokeapi.co/api/v2/ability/31/"
                    ),
                    isHidden = true
                )
            ),
            types = listOf()
        )

        coEvery { casoUso1.invoke(Mockito.anyString()) } returns flowOf(
            Resource.Success(
                validatedDetail
            )
        )

        viewModel.obtainPokemonDetail(Mockito.anyString())


    }

    @Test
    fun `when obtain ability detail`() = runTest {
        val validatedAbility = ValidatedAbilityDetail("a", "a")

        coEvery { casoUso2.invoke(Mockito.anyString()) } returns flowOf(
            Resource.Success(
                validatedAbility
            )
        )

        viewModel.obtainAbilityDetail(Mockito.anyString())

        assertEquals(viewModel.abilityDetailData.getOrAwaitValue(), validatedAbility)

        assertFalse(viewModel.isLoading.getOrAwaitValue())
    }

    @Test
    fun `when obtain error on pokemon detail`() = runTest {
        coEvery { casoUso1.invoke(Mockito.anyString()) } returns flowOf(Resource.Error("error example"))

        viewModel.obtainPokemonDetail(Mockito.anyString())

        assertEquals(
            viewModel.toastErrorMessage.value,
            "error example"
        )
    }

    @Test
    fun `when obtain error on pokemon ability detail`() = runTest {
        coEvery { casoUso2.invoke(Mockito.anyString()) } returns flowOf(Resource.Error("error example"))

        viewModel.obtainAbilityDetail(Mockito.anyString())

        assertEquals(
            viewModel.toastErrorMessage.value,
            "error example"
        )
    }


}