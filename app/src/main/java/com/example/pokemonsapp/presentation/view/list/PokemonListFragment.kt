package com.example.pokemonsapp.presentation.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonsapp.R
import com.example.pokemonsapp.databinding.FragmentPokemonListBinding
import com.example.pokemonsapp.presentation.view.BaseVMFragment
import com.example.pokemonsapp.presentation.view.list.adapter.PokemonAdapter
import com.example.pokemonsapp.presentation.view.list.viewmodel.PokemonListViewModel


class PokemonListFragment : BaseVMFragment<PokemonListViewModel, FragmentPokemonListBinding>(),
    PokemonAdapter.OnPokemonClickListener {

    override val viewModel: PokemonListViewModel by viewModels()

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentPokemonListBinding.inflate(layoutInflater)

    override fun onViewModelCreated() {
        viewModel.pokemonFilteredList.observe(this) { pokemonList ->
            binding.rvPokemonList.adapter = PokemonAdapter(pokemonList, this)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            updateLoadingVisibility(isLoading)
        }

        if (!viewModel.hasLoadedData) {
            viewModel.obtainPokemonList()
        }
    }

    override fun initUi() {
        initSearchComponent()
        initRecyclerViewConfiguration()
    }

    /* UI METHODS */
    override fun onPokemonClick(name: String) {
        val action = PokemonListFragmentDirections.actionPokemonListToPokemonDetail(name)
        findNavController().navigate(action)
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
                getColumnsByScreenWidth(),
                GridLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun getColumnsByScreenWidth() =
        resources.displayMetrics.widthPixels /
                resources.getDimensionPixelSize(R.dimen.pokemon_card_width)


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