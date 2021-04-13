package com.example.lec12database.models;

import android.content.Context;

import androidx.room.Room;

public class SongDataSource {
    private SongDatabase db;

    public SongDataSource(Context context) {
        db = Room.databaseBuilder(context,
                SongDatabase.class, "SongDatabase").build();
    }

    public SongDatabase getDb() {
        return db;
    }
}
