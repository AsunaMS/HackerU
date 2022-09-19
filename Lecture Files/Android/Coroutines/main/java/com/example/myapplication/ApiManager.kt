package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    companion object {
        private const val apiAddress: String = "https://rickandmortyapi.com/api/"
        private val instance: Retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(apiAddress).build()
        val rickAndMortyDao: RickAndMortyDao = instance.create(RickAndMortyDao::class.java)
    }
}