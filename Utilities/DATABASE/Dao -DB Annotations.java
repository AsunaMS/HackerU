package com.example.lec12database.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM Song ORDER BY id ASC")
    List<Song> getAllSongs();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Song song);

    @Delete
    void remove(Song song);

    @Insert
    void add(Song... song);

    @Update
    void update(Song song);

}
