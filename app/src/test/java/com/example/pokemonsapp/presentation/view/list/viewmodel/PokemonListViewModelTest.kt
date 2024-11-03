package com.example.pokemonsapp.presentation.view.list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokemonsapp.data.api.models.ValidatedPokemonModel
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.domain.usecases.GetPokemonListUseCase
import com.example.pokemonsapp.getOrAwaitValue
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
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonListViewModelTest {
    private lateinit var viewModel: PokemonListViewModel

    @MockK
    private lateinit var casoUso: GetPokemonListUseCase

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = PokemonListViewModel(casoUso)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when obtain pokemon list and success`() = runTest {
        val list = listOf(ValidatedPokemonModel("a", "a"))
        coEvery { casoUso.invoke() } returns flowOf(Resource.Success(list))

        viewModel.obtainPokemonList()

        Assert.assertEquals(
            list,
            viewModel.pokemonList.value
        )
        Assert.assertFalse(viewModel.isLoading.getOrAwaitValue())
    }

    @Test
    fun `when obtain pokemon list and error`() = runTest {
        coEvery { casoUso.invoke() } returns flowOf(Resource.Error("error example"))

        viewModel.obtainPokemonList()

        Assert.assertEquals(
            viewModel.toastErrorMessage.value,
            "error example"
        )
    }

    @Test
    fun `when filter pokemon list with existing name`() {
        val list = listOf(
            ValidatedPokemonModel("1", "Pikachu"),
            ValidatedPokemonModel("2", "Bulbasaur")
        )
        viewModel.pokemonList.postValue(list)

        viewModel.filterPokemonList("Pika")

        val filteredList = viewModel.pokemonFilteredList.getOrAwaitValue()
        Assert.assertNotNull(filteredList)
        Assert.assertEquals(1, filteredList.size)
        Assert.assertEquals("Pikachu", filteredList[0].name)
    }

    @Test
    fun `when filter pokemon list with empty space`() {
        val list = listOf(
            ValidatedPokemonModel("1", "Pikachu"),
            ValidatedPokemonModel("2", "Bulbasaur")
        )
        viewModel.pokemonList.postValue(list)

        viewModel.filterPokemonList("")

        val filteredList = viewModel.pokemonFilteredList.getOrAwaitValue()
        Assert.assertNotNull(filteredList)
        Assert.assertEquals(filteredList.size, list.size)
    }
}