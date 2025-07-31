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
import com.example.pokemonsapp.presentation.view.list.adapter.GridSpacingItemDecoration
import com.example.pokemonsapp.presentation.view.list.adapter.PokemonAdapter
import com.example.pokemonsapp.presentation.view.list.viewmodel.PokemonListViewModel

class PokemonListFragment : BaseVMFragment<PokemonListViewModel, FragmentPokemonListBinding>(),
    PokemonAdapter.OnPokemonClickListener {

    override val viewModel: PokemonListViewModel by viewModels()

    private lateinit var adapter: PokemonAdapter

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentPokemonListBinding.inflate(inflater)

    override fun initUi() {
        adapter = PokemonAdapter(this)
        binding.rvPokemonList.adapter = adapter
        initRecyclerViewConfiguration()
        initSearchComponent()
    }

    override fun onViewModelCreated() {
        viewModel.pokemonFilteredList.observe(viewLifecycleOwner) { pokemonList ->
            adapter.submitList(pokemonList)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            updateLoadingVisibility(isLoading)
        }

        if (!viewModel.hasLoadedData) {
            viewModel.obtainPokemonList()
        }
    }

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
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        val columns = getColumnsByScreenWidth()

        binding.rvPokemonList.layoutManager = GridLayoutManager(
            context,
            columns,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.rvPokemonList.addItemDecoration(GridSpacingItemDecoration(columns, spacingInPixels, true))
    }

    private fun getColumnsByScreenWidth() =
        resources.displayMetrics.widthPixels /
                (resources.getDimensionPixelSize(R.dimen.pokemon_card_width) + 8)

    private fun updateLoadingVisibility(isLoading: Boolean) {
        binding.loadingView.root.visibility = if (isLoading) {
            binding.group.visibility = View.GONE
            binding.loadingView.lottieLoader.playAnimation()
            View.VISIBLE
        } else {
            binding.group.visibility = View.VISIBLE
            binding.loadingView.lottieLoader.playAnimation()
            View.GONE
        }
    }
}
