package com.example.pokemonsapp.domain.usecases

import com.example.pokemonsapp.data.api.models.AbilityDetailResponse
import com.example.pokemonsapp.data.api.models.PokemonDetailResponse
import com.example.pokemonsapp.data.api.models.PokemonResponse
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class GetAbilityDetailUseCase(
    private val repository: PokemonRepository
) {
    fun invoke(name : String): Flow<Resource<AbilityDetailResponse>> =
        flow {
            emit(Resource.Loading)
            delay(2500)
            val response: Resource<AbilityDetailResponse> = try {
                val response = repository.getAbilityDetail(name)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Resource.Success(it)
                    } ?: Resource.Error("Respuesta vacía")
                } else {
                    Resource.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: IOException) {
                Resource.Error("Error de red: ${e.localizedMessage}")
            } catch (e: Exception) {
                Resource.Error("Excepción: ${e.localizedMessage}")
            }

            emit(response)
        }.flowOn(Dispatchers.IO)
}