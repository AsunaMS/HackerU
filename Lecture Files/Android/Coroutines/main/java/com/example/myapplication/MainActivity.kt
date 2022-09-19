package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*


class RickAndMortyCharactersRvAdapter(
    private val characters: List<RickAndMortyCharacter>,
) :
    RecyclerView.Adapter<RickAndMortyCharactersRvAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cNameTextView: TextView = itemView.findViewById(R.id.cName)
        val cRegionTextView: TextView = itemView.findViewById(R.id.cEpisode_Region)
        val cEpisodeAppearancesTextView: TextView = itemView.findViewById(R.id.cEpisode_Appearances)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.cNameTextView.text = "${character.name}"
        holder.cRegionTextView.text = "Origin: ${character.origin.name}"
        holder.cEpisodeAppearancesTextView.text = "Appearances: ${character.episode.size}"
    }

    override fun getItemCount(): Int {
        return characters.size
    }
}

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MyViewModel

    lateinit var rvAdapter: RickAndMortyCharactersRvAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        recyclerView = findViewById(R.id.rickAndMortyRv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.rickAndMortyLiveData.observe(this) {
            rvAdapter = RickAndMortyCharactersRvAdapter(it.results)
            recyclerView.adapter = rvAdapter
        }


        MainScope().launch {

            // IO Thread - IO Operations
            withContext(Dispatchers.IO) {

            }
            // Main Thread - UI Operations
            withContext(Dispatchers.Main) {

            }


            // suspend function which awaits result
            suspend fun getCharacters(): RickAndMortyCharacters {
                return async {
                    return@async viewModel.getCharactersAwait()
                }.await()
            }

            // use the function to reach results after received
            getCharacters().results
        }


    }
}