package com.example.myapplication

import retrofit2.http.GET

interface RickAndMortyDao {
    @GET("character")
    suspend fun getCharacters(): RickAndMortyCharacters
}