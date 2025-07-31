package com.example.pokemonsapp.presentation.view.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pokemonsapp.R
import com.example.pokemonsapp.databinding.ActivityMainBinding
import com.example.pokemonsapp.databinding.FragmentHomeBinding
import com.example.pokemonsapp.presentation.view.BaseFragment
import com.example.pokemonsapp.presentation.view.BaseVMFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentHomeBinding.inflate(layoutInflater)

    override fun initUi() {
        initBoton()
    }

    private fun initBoton() {
        val action = HomeFragmentDirections.actionHomeToPokemonList()
        binding.boton.setOnClickListener {
            findNavController().navigate(action)
        }
    }
}