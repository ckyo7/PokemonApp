package com.example.pokemonsapp.presentation.view.detail.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.example.pokemonsapp.databinding.FragmentPokemonAbilitiesBinding
import com.example.pokemonsapp.presentation.GlobalConstants
import com.example.pokemonsapp.presentation.view.BaseVMFragment
import com.example.pokemonsapp.presentation.view.detail.viewmodels.PokemonDetailViewModel


class PokemonAbilityDetailFragment :
    BaseVMFragment<PokemonDetailViewModel, FragmentPokemonAbilitiesBinding>() {
    override val viewModel: PokemonDetailViewModel by activityViewModels()
    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentPokemonAbilitiesBinding.inflate(inflater, container, attachToParent)

    override fun onViewModelCreated() {
        viewModel.abilityDetailData.observe(this) {
            binding.abilityTittle.text = it.name.replace(
                GlobalConstants.HYPHEN_CHAR,
                GlobalConstants.EMPTY_TEXT_SPACE
            ).replaceFirstChar { char -> char.uppercase() }
            binding.abilityDescription.text = it.effect
        }

        arguments?.getString(INTENT_ABILITY_NAME)?.let {
            viewModel.obtainAbilityDetail(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.abilityDetailData = MutableLiveData()
    }

    companion object {
        const val INTENT_ABILITY_NAME = "INTENT_ABILITY_NAME"
    }
}