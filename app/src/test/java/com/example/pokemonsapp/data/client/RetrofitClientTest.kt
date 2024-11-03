package com.example.pokemonsapp.data.client


import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Test

class RetrofitClientTest {

    @Test
    fun `should have the correct base URL`() {
        val retrofitInstance = RetrofitClient.instance
        assertEquals("https://pokeapi.co/api/v2/", retrofitInstance.baseUrl().toString())
    }

    @Test
    fun `should have an OkHttpClient with correct timeouts`() {
        val retrofitInstance = RetrofitClient.instance
        val okHttpClient = retrofitInstance.callFactory() as OkHttpClient

        assertEquals(30, okHttpClient.connectTimeoutMillis() / 1000)
        assertEquals(30, okHttpClient.readTimeoutMillis() / 1000)
        assertEquals(30, okHttpClient.writeTimeoutMillis() / 1000)
    }
}