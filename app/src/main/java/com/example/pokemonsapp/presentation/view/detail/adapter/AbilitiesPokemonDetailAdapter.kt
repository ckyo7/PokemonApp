package com.example.pokemonsapp.presentation.view.detail.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonsapp.R
import com.example.pokemonsapp.data.api.models.Ability
import com.example.pokemonsapp.data.api.models.PokemonModel

class AbilitiesPokemonDetailAdapter(
    private val pokemonList: List<Ability>,
    val listener: OnPokemonClickListener
) :
    RecyclerView.Adapter<AbilitiesPokemonDetailAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_image)
        val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)


        fun bind(pokemon: Ability) {

        }

        private fun getImageUrlFromPokemonNumber(url: String): String {
            val pokemonId = extractPokemonIdFromUrl(url)
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
        }

        private fun extractPokemonIdFromUrl(url: String): String {
            val urlParts = url.split("/")
            return urlParts[urlParts.lastIndex - 1]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.pokemon_card,
            parent, false
        )
        return PokemonViewHolder(view)
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