package com.example.pokemonsapp.presentation.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.pokemonsapp.R
import com.example.pokemonsapp.data.api.services.PokemonService
import com.example.pokemonsapp.data.client.RetrofitClient
import com.example.pokemonsapp.databinding.ActivityMainBinding
import com.example.pokemonsapp.presentation.view.pokemonlist.PokemonListActivity
import com.example.pokemonsapp.presentation.view.pokemonlist.viewmodel.ListadoPokemonViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root);

        initBoton()
    }

    private fun initBoton(){
        binding.boton.setOnClickListener {
            navigateToPokemonList()
        }
    }

    private fun navigateToPokemonList() {
        startActivity(Intent(this, PokemonListActivity::class.java))
        finish()
    }
}