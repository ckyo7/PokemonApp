package com.example.pokemonsapp.domain.usecases

import com.example.pokemonsapp.data.api.models.AbilityDetailResponse
import com.example.pokemonsapp.data.api.models.ValidatedAbilityDetail
import com.example.pokemonsapp.data.repository.PokemonRepository
import com.example.pokemonsapp.domain.Resource
import com.example.pokemonsapp.presentation.utils.validateAbilityDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

class GetAbilityDetailUseCase(
    private val repository: PokemonRepository
) {
    fun invoke(name: String): Flow<Resource<ValidatedAbilityDetail>> =
        flow {
            kotlinx.coroutines.delay(2500)
            val response: Resource<ValidatedAbilityDetail> = try {
                val response = repository.getAbilityDetail(name)
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

    private fun handleResponse(response: Response<AbilityDetailResponse>): Resource<ValidatedAbilityDetail> {
        return validateAbilityDetail(response.body()).let { validatedResponse ->
            if (validatedResponse != null) {
                Resource.Success(validatedResponse)
            } else {
                Resource.Error("Wrong data from Service")
            }
        }
    }
}