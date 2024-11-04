package com.example.pokemonsapp.domain.usecases

import com.example.pokemonsapp.data.api.models.PokemonResponse
import com.example.pokemonsapp.data.api.models.ValidatedPokemonModel
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.domain.interfaces.IPokemonRepository
import com.example.pokemonsapp.presentation.utils.getPokemonListValidated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

class GetPokemonListUseCase(
    private val repository: IPokemonRepository
) {
    fun invoke() = flow {
        kotlinx.coroutines.delay(2500)
        val response: Resource<List<ValidatedPokemonModel>> = try {
            val response = repository.getPokemonList()
            if (response.isSuccessful) {
                handleResponse(response)
            } else {
                Resource.Error("Error ${response.code()}: ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Api error : ${e.localizedMessage}")
        } catch (e: Exception) {
            Resource.Error("Error: ${e.localizedMessage}")
        }

        emit(response)
    }.flowOn(Dispatchers.IO)

    private fun handleResponse(response: Response<PokemonResponse>): Resource<List<ValidatedPokemonModel>> {
        return getPokemonListValidated(response.body()).let { validatedResponse ->
            if (validatedResponse.isNotEmpty()) {
                Resource.Success(validatedResponse)
            } else {
                Resource.Error("Wrong data from Service")
            }
        }
    }
}
