package com.example.pokemonsapp.presentation.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import com.example.pokemonsapp.data.api.models.PokemonModel
import kotlin.math.absoluteValue

object  PokemonUtil {
    fun getDominantColor(bitmap: Bitmap): Int {
        val palette = Palette.from(bitmap).generate()
        return palette.dominantSwatch?.rgb ?: 0 // Default to black if no dominant color is found
    }
}