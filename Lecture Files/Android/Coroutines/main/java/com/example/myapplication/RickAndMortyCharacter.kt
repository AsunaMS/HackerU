package com.example.myapplication


data class RickAndMortyLocation(
    val name: String,
    val url: String
)

data class RickAndMortyCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: RickAndMortyLocation,
    val location: RickAndMortyLocation,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)