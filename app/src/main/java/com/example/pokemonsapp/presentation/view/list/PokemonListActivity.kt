package com.example.pokemonsapp.presentation.view.list

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonsapp.R
import com.example.pokemonsapp.databinding.ActivityPokemonListBinding
import com.example.pokemonsapp.presentation.BaseVMActivity
import com.example.pokemonsapp.presentation.view.detail.PokemonDetailActivity
import com.example.pokemonsapp.presentation.view.list.adapter.PokemonAdapter
import com.example.pokemonsapp.presentation.view.list.viewmodel.PokemonListViewModel

class PokemonListActivity : BaseVMActivity<PokemonListViewModel, ActivityPokemonListBinding>(),
    PokemonAdapter.OnPokemonClickListener {
    override val viewModel: PokemonListViewModel by viewModels()

    override fun initViewBinding() = ActivityPokemonListBinding.inflate(layoutInflater)

    override fun onViewBindingCreated() {
        initSearchComponent()
        initRecyclerViewConfiguration()
    }

    override fun onViewModelCreated() {
        viewModel.pokemonFilteredList.observe(this) { pokemonList ->
            binding.rvPokemonList.adapter = PokemonAdapter(pokemonList, this)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            updateLoadingVisibility(isLoading)
        }

        viewModel.obtainPokemonList()
    }

    /* UI METHODS */
    override fun onPokemonClick(name: String) {
        startActivity(Intent(this, PokemonDetailActivity::class.java).apply {
            putExtra(PokemonDetailActivity.INTENT_POKEMON_DETAIL_NAME, name)
        })
    }

    private fun initSearchComponent() {
        binding.searchEditText.addTextChangedListener { textWritten ->
            viewModel.filterPokemonList(textWritten.toString())
        }
    }

    private fun initRecyclerViewConfiguration() {
        binding.rvPokemonList.apply {
            layoutManager = GridLayoutManager(
                context,
                resources.displayMetrics.widthPixels / resources.getDimensionPixelSize(R.dimen.pokemon_card_width),
                GridLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun updateLoadingVisibility(isLoading: Boolean) {
        binding.loadingView.root.visibility = if (isLoading) {
            binding.group.visibility = View.GONE
            View.VISIBLE
        } else {
            binding.group.visibility = View.VISIBLE
            View.GONE
        }
    }
}
