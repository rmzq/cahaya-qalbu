package com.example.e_pertanian.note;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.e_pertanian.note.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY note_id DESC")
    List<Note> getAll();

    @Insert
    void insert(Note note);

    @Update
    void update(Note repos);

    @Delete
    void delete(Note note);

    @Delete
    void delete(Note... note);      // Note... is varargs, here note is an array
}
