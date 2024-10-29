package com.example.pokemonsapp.presentation.view.pokemonlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonsapp.R
import com.example.pokemonsapp.data.api.models.PokemonModel
import com.squareup.picasso.Picasso
import java.util.Locale

class PokemonAdapter(private val pokemonList: List<PokemonModel>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_image)
        val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)

        fun bind(pokemon: PokemonModel) {
            pokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            Picasso.get()
                .load(getImageUrlFromPokemonNumber(pokemon.url))
                .placeholder(R.mipmap.ic_snorlak_placeholder)
                .into(pokemonImage);
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
}