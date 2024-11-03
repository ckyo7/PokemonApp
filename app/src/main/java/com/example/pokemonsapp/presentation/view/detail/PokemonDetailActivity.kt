package com.example.pokemonsapp.presentation.view.detail

import android.view.View
import androidx.activity.viewModels
import com.example.pokemonsapp.R
import com.example.pokemonsapp.databinding.ActivityPokemonDetailBinding
import com.example.pokemonsapp.presentation.BaseVMActivity
import com.example.pokemonsapp.presentation.view.detail.fragments.PokemonAbilityDetailFragment
import com.example.pokemonsapp.presentation.view.detail.fragments.PokemonDetailFragment
import com.example.pokemonsapp.presentation.view.detail.viewmodels.PokemonDetailViewModel

class PokemonDetailActivity :
    BaseVMActivity<PokemonDetailViewModel, ActivityPokemonDetailBinding>() {

    override val viewModel: PokemonDetailViewModel by viewModels()

    override fun initViewBinding() = ActivityPokemonDetailBinding.inflate(layoutInflater)

    override fun onViewBindingCreated() {

        binding.buttonDetail.setOnClickListener {
            buttonClickListener()
        }

        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragment_container,
                PokemonDetailFragment()
            ).commit()
    }

    override fun onViewModelCreated() {
        viewModel.isLoading.observe(this) {
            binding.loadingView.root.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.abilityClicked.observe(this) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    PokemonAbilityDetailFragment()
                ).addToBackStack(null).commit()
        }
    }

    /* UI ACTIVITY METHODS*/
    private fun buttonClickListener() {
        val currentFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
        when (currentFragment) {
            is PokemonDetailFragment -> {
                finish()
            }

            is PokemonAbilityDetailFragment -> {
                supportFragmentManager.popBackStack()
            }

            else -> {
                //Handle error
            }
        }
    }


    companion object {
        const val INTENT_POKEMON_DETAIL_NAME = "INTENT_POKEMON_DETAIL_NAME"
    }
}