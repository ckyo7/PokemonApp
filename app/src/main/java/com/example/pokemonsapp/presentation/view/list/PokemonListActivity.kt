package com.example.pokemonsapp.presentation.view.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonsapp.databinding.ActivityPokemonListBinding
import com.example.pokemonsapp.presentation.view.detail.PokemonDetailActivity
import com.example.pokemonsapp.presentation.view.list.adapter.PokemonAdapter
import com.example.pokemonsapp.presentation.view.list.viewmodel.PokemonListViewModel

class PokemonListActivity : AppCompatActivity(), PokemonAdapter.OnPokemonClickListener {
    override fun onPokemonClick(name: String) {
        startActivity(Intent(this, PokemonDetailActivity::class.java))
    }

    private lateinit var binding: ActivityPokemonListBinding
    private val viewModel = PokemonListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModelLiveData()

        viewModel.obtainPokemonList()

        initSearchComponent()
        initRecyclerViewConfiguration()
    }

    private fun initSearchComponent() {
        binding.searchEditText.addTextChangedListener { textWritten ->
            viewModel.filterPokemonList(textWritten.toString())
        }
    }

    private fun initRecyclerViewConfiguration() {
        binding.rvPokemonList.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun observeViewModelLiveData() {
        viewModel.pokemonFilteredList.observe(this) {
            binding.rvPokemonList.adapter = PokemonAdapter(it, this)
        }
    }
}
