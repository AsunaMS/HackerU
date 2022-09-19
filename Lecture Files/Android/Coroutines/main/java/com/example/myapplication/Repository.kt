package com.example.myapplication

class Repository {

    suspend fun getCharacters(): RickAndMortyCharacters {
        return ApiManager.rickAndMortyDao.getCharacters()
    }
}