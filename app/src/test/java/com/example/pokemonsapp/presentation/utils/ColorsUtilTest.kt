package com.example.pokemonsapp.presentation.utils

import android.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ColorsUtilTest {

    @Test
    fun `getColorByType returns correct color for known types`() {
        assertEquals(Color.parseColor("#A8A77A"), getColorByType("NORMAL"))
        assertEquals(Color.parseColor("#EE8130"), getColorByType("FIRE"))
        assertEquals(Color.parseColor("#6390F0"), getColorByType("WATER"))
        assertEquals(Color.parseColor("#F7D02C"), getColorByType("ELECTRIC"))
        assertEquals(Color.parseColor("#7AC74C"), getColorByType("GRASS"))
        assertEquals(Color.parseColor("#96D9D6"), getColorByType("ICE"))
        assertEquals(Color.parseColor("#C22E28"), getColorByType("FIGHTING"))
        assertEquals(Color.parseColor("#A33EA1"), getColorByType("POISON"))
        assertEquals(Color.parseColor("#E2BF65"), getColorByType("GROUND"))
        assertEquals(Color.parseColor("#A98FF3"), getColorByType("FLYING"))
        assertEquals(Color.parseColor("#F95587"), getColorByType("PSYCHIC"))
        assertEquals(Color.parseColor("#A6B91A"), getColorByType("BUG"))
        assertEquals(Color.parseColor("#B6A136"), getColorByType("ROCK"))
        assertEquals(Color.parseColor("#735797"), getColorByType("GHOST"))
        assertEquals(Color.parseColor("#6F35FC"), getColorByType("DRAGON"))
        assertEquals(Color.parseColor("#705746"), getColorByType("DARK"))
        assertEquals(Color.parseColor("#D685AD"), getColorByType("FAIRY"))
        assertEquals(Color.parseColor("#B7B7CE"), getColorByType("STEEL"))
    }

    @Test
    fun `getColorByType returns color for unknown type`() {
        assertEquals(Color.parseColor("#68A090"), getColorByType("UNKNOWN_TYPE"))
    }
}