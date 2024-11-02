package com.example.pokemonsapp.presentation.view.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pokemonsapp.databinding.FragmentPokemonAbilitiesBinding
import com.example.pokemonsapp.presentation.view.BaseVMFragment
import com.example.pokemonsapp.presentation.view.detail.viewmodels.PokemonDetailViewModel


class PokemonAbilitiesFragment :
    BaseVMFragment<PokemonDetailViewModel, FragmentPokemonAbilitiesBinding>() {
    override val viewModel: PokemonDetailViewModel by activityViewModels()
    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentPokemonAbilitiesBinding.inflate(inflater, container, attachToParent)

    override fun onViewModelCreated() {
        //Not yet
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}