package com.example.pokemonsapp.presentation.utils

import android.graphics.Bitmap
import androidx.palette.graphics.Palette

object PokemonUtil {
    fun getDominantColor(bitmap: Bitmap): Int {
        val palette = Palette.from(bitmap).generate()
        return palette.dominantSwatch?.rgb ?: 0 // Default to black if no dominant color is found
    }


    fun getColorByType(type: String) = PokemonType.fromString(type).getColor()

    enum class PokemonType(val colorHex: String) {
        NORMAL("#A8A77A"),
        FIRE("#EE8130"),
        WATER("#6390F0"),
        ELECTRIC("#F7D02C"),
        GRASS("#7AC74C"),
        ICE("#96D9D6"),
        FIGHTING("#C22E28"),
        POISON("#A33EA1"),
        GROUND("#E2BF65"),
        FLYING("#A98FF3"),
        PSYCHIC("#F95587"),
        BUG("#A6B91A"),
        ROCK("#B6A136"),
        GHOST("#735797"),
        DRAGON("#6F35FC"),
        DARK("#705746"),
        FAIRY("#D685AD"),
        STEEL("#B7B7CE"),
        UNKNOWN("#68A090"); // Tipo desconocido

        companion object {
            fun fromString(type: String): PokemonType {
                return entries.find { it.name.equals(type, ignoreCase = true) } ?: UNKNOWN
            }
        }

        fun getColor(): Int {
            return android.graphics.Color.parseColor(colorHex)
        }
    }
}