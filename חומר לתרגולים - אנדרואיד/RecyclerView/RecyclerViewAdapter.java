package com.example.entitiesexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Note> allNotes;

    public RecyclerViewAdapter() {
        this.allNotes = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {
        // Populate our holder here
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class RecyclerViewAdapter extends RecyclerView.ViewHolder {
       // Our Views

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // findViewById here

        }
    }
}
