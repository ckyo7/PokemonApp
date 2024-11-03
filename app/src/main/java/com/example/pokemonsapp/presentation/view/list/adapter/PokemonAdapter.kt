package com.example.pokemonsapp.presentation.view.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonsapp.R
import com.example.pokemonsapp.data.api.models.ValidatedPokemonModel
import com.example.pokemonsapp.databinding.PokemonCardBinding
import com.example.pokemonsapp.presentation.GlobalConstants

class PokemonAdapter(
    private val pokemonList: List<ValidatedPokemonModel>,
    val listener: OnPokemonClickListener
) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(private val binding: PokemonCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: ValidatedPokemonModel) {
            binding.pokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }

            try {
                Glide.with(itemView).load(getImageUrlFromPokemonNumber(pokemon.url))
                    .error(R.drawable.ic_launcher_foreground)
                    .placeholder(R.mipmap.ic_snorlak_placeholder)
                    .into(binding.pokemonImage)
            } catch (exception: Exception) {
                Glide.with(itemView).load(R.drawable.ic_launcher_foreground)
                    .into(binding.pokemonImage)
                Log.d(
                    null,
                    exception.message ?: "Error has occurred when catching image from server"
                )

            }
            itemView.rootView.setOnClickListener {
                listener.onPokemonClick(pokemon.name)
            }
        }

        private fun getImageUrlFromPokemonNumber(url: String): String {
            val pokemonId = extractPokemonIdFromUrl(url)
            return itemView.context.getString(R.string.url_pokemon_image, pokemonId)
        }

        private fun extractPokemonIdFromUrl(url: String): String {
            val urlParts = url.split(GlobalConstants.BAR_RIGHT_CHAR)
            return urlParts[urlParts.lastIndex - 1]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            PokemonCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    interface OnPokemonClickListener {
        fun onPokemonClick(name: String)
    }
}