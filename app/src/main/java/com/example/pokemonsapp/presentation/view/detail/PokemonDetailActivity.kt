package com.example.pokemonsapp.presentation.view.detail

import android.view.View
import androidx.activity.viewModels
import com.example.pokemonsapp.R
import com.example.pokemonsapp.databinding.ActivityPokemonDetailBinding
import com.example.pokemonsapp.presentation.BaseVMActivity
import com.example.pokemonsapp.presentation.view.detail.fragments.PokemonAbilitiesFragment
import com.example.pokemonsapp.presentation.view.detail.fragments.PokemonDetailFragment
import com.example.pokemonsapp.presentation.view.detail.viewmodels.PokemonDetailViewModel

class PokemonDetailActivity :
    BaseVMActivity<PokemonDetailViewModel, ActivityPokemonDetailBinding>() {

    companion object {
        const val INTENT_POKEMON_DETAIL_NAME = "INTENT_POKEMON_DETAIL_NAME"
    }

    override val viewModel: PokemonDetailViewModel by viewModels()

    override fun initViewBinding() = ActivityPokemonDetailBinding.inflate(layoutInflater)

    override fun onViewBindingCreated() {

        binding.buttonDetail.setOnClickListener {
            val currentFragment =
                supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
            when (currentFragment) {
                is PokemonDetailFragment -> {
                    finish()
                }

                is PokemonAbilitiesFragment -> {
                    supportFragmentManager.popBackStack()
                }

                else -> {
                    //Handle error
                }
            }
        }

        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragment_container,
                PokemonDetailFragment()
            ) // Reemplaza el contenido del contenedor
            .commit() // Realiza la transacción
    }

    override fun onViewModelCreated() {
        viewModel.isLoading.observe(this) {
            binding.loadingView.root.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}