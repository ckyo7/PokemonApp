package com.example.pokemonsapp.domain.usecases

import com.example.pokemonsapp.data.api.models.PokemonDetailResponse
import com.example.pokemonsapp.data.api.models.ValidatedPokemonDetail
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.presentation.utils.getValidateAndCreateValidatedPokemonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

class GetPokemonDetailUseCase(
    private val repository: PokemonRepository
) {
    fun invoke(name: String): Flow<Resource<ValidatedPokemonDetail>> =
        flow {
            delay(2500)
            val response: Resource<ValidatedPokemonDetail> = try {
                val response = repository.getPokemonDetail(name)
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

    private fun handleResponse(response: Response<PokemonDetailResponse>): Resource<ValidatedPokemonDetail> {
        return getValidateAndCreateValidatedPokemonDetail(response.body())
            .let { validatedResponse ->
                if (validatedResponse != null) {
                    Resource.Success(validatedResponse)
                } else {
                    Resource.Error("Wrong data from Service")
                }
            }
    }
}