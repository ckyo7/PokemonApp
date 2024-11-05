package com.example.pokemonsapp.presentation.view.detail.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonsapp.R
import com.example.pokemonsapp.databinding.FragmentPokemonDetailBinding
import com.example.pokemonsapp.presentation.GlobalConstants
import com.example.pokemonsapp.presentation.view.BaseVMFragment
import com.example.pokemonsapp.presentation.view.detail.PokemonDetailActivity.Companion.INTENT_POKEMON_DETAIL_NAME
import com.example.pokemonsapp.presentation.view.detail.adapter.AbilitiesPokemonDetailAdapter
import com.example.pokemonsapp.presentation.view.detail.viewmodels.PokemonDetailViewModel

class PokemonDetailFragment :
    BaseVMFragment<PokemonDetailViewModel, FragmentPokemonDetailBinding>(),
    AbilitiesPokemonDetailAdapter.OnAbilityClickListener {
    override val viewModel: PokemonDetailViewModel by activityViewModels()

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentPokemonDetailBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.visibility = View.VISIBLE
        initAbilityRecyclcerView()
    }

    @SuppressLint("DefaultLocale")
    override fun onViewModelCreated() {
        viewModel.nameWrapper.observe(this) { screenData ->
            binding.pokemonName.text = getString(
                R.string.text_name_pokemon_detail,
                screenData.id,
                screenData.name
            )
            binding.pokemonBaseExp.text =
                getString(R.string.text_base_experience_pokemon_detail, screenData.baseExperience)
        }

        viewModel.frontSprite.observe(this) { sprite ->
            Glide.with(requireContext()).load(sprite)
                .error(R.drawable.ic_logo)
                .into(binding.pokemonImage)
        }

        viewModel.heightData.observe(this) {
            binding.pokemonHeight.text =
                getString(
                    R.string.text_height_pokemon_detail,
                    String.format(
                        GlobalConstants.PATTERN_TWO_DECIMAL,
                        it / GlobalConstants.CONVERSION_RATE
                    )
                )
        }

        viewModel.weightData.observe(this) {
            binding.pokemonWeight.text =
                getString(
                    R.string.text_weight_pokemon_detail, String.format(
                        GlobalConstants.PATTERN_TWO_DECIMAL,
                        it / GlobalConstants.CONVERSION_RATE
                    )
                )
        }

        viewModel.typesWrapper.observe(this) { typeList ->
            var typeNumber = 0
            typeList.forEach { typeWrapper ->
                initTypeTextView(typeWrapper.type, typeWrapper.typeColor, typeNumber)
                typeNumber++
            }
        }

        viewModel.abilityList.observe(this) { abilities ->
            binding.rvAbilities.adapter = AbilitiesPokemonDetailAdapter(abilities, this)
        }

        activity?.intent?.getStringExtra(INTENT_POKEMON_DETAIL_NAME)?.let {
            viewModel.obtainPokemonDetail(it)
        } ?: {
            //show error
        }
    }

    /*  INIT UI METHODS */
    private fun initAbilityRecyclcerView() {
        binding.rvAbilities.apply {
            layoutManager = object : LinearLayoutManager(context) { override fun canScrollVertically() = false }
        }
    }

    private fun initTypeTextView(type: String, typeColor: Int, typeNumber: Int) {
        val textView = getTextViewByTypeNumber(typeNumber)
        setTypeTextViewProperties(textView, type, typeColor)
    }

    private fun getTextViewByTypeNumber(typeNumber: Int) =
        when (typeNumber) {
            0 -> binding.pokemonType
            1 -> binding.pokemonType2
            else -> throw IllegalArgumentException("Invalid type number: $typeNumber")
        }

    private fun setTypeTextViewProperties(textView: TextView, type: String, typeColor: Int) {
        textView.apply {
            text = type
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(typeColor)
                cornerRadius = 20f
            }
            visibility = View.VISIBLE
        }
    }

    override fun onAbilityClick(name: String) {
        viewModel.abilityClicked.postValue(name)
    }
}