package com.example.pokemonsapp.presentation.view.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonsapp.databinding.PokemonAbilityBinding
import com.example.pokemonsapp.presentation.view.detail.viewmodels.PokemonDetailViewModel

class AbilitiesPokemonDetailAdapter(
    private val abilities: List<PokemonDetailViewModel.Companion.AbilityWrapper>,
    val listener: OnAbilityClickListener
) :
    RecyclerView.Adapter<AbilitiesPokemonDetailAdapter.AbilityViewHolder>() {

    inner class AbilityViewHolder(private val binding: PokemonAbilityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ability: PokemonDetailViewModel.Companion.AbilityWrapper) {
            binding.abilityName.text = ability.formattedName
            binding.abilityIcon.setOnClickListener {
                listener.onAbilityClick(ability.originalName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder {
        val binding =
            PokemonAbilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AbilityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) {
        holder.bind(abilities[position])
    }

    override fun getItemCount() = abilities.size

    interface OnAbilityClickListener {
        fun onAbilityClick(name: String)
    }
}