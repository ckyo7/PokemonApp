package com.example.pokemonsapp.presentation.view.pokemonlist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonsapp.R
import com.example.pokemonsapp.databinding.ActivityMainBinding
import com.example.pokemonsapp.databinding.ActivityPokemonListBinding
import com.example.pokemonsapp.presentation.view.pokemonlist.adapter.PokemonAdapter
import com.example.pokemonsapp.presentation.view.pokemonlist.viewmodel.ListadoPokemonViewModel

class PokemonListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokemonListBinding
    private val viewModel = ListadoPokemonViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModelLiveData()

        viewModel.obtainPokemonList()
    }

    private fun observeViewModelLiveData() {
        viewModel.pokemonList.observe(this) {
            val recyclerView = binding.rvPokemonList
            val layoutManager = GridLayoutManager(this,3, GridLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = PokemonAdapter(it)
        }
    }
}
