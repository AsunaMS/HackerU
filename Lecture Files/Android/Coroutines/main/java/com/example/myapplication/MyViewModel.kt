package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {

    private val repository: Repository = Repository()
    private val _rickAndMortyLiveData: MutableLiveData<RickAndMortyCharacters> = MutableLiveData()
    val rickAndMortyLiveData: LiveData<RickAndMortyCharacters> = _rickAndMortyLiveData

    init {
        viewModelScope.launch {
            val characters = repository.getCharacters()
            _rickAndMortyLiveData.postValue(characters)
        }
    }


    suspend fun getCharactersAwait(): RickAndMortyCharacters {

        return withContext(viewModelScope.coroutineContext) {
            val chars = repository.getCharacters()
            chars
        }
    }


}