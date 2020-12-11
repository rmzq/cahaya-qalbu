package com.example.e_pertanian.note;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName="notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int note_id;

    @ColumnInfo(name ="title")
    public String title;

    @ColumnInfo(name ="sub_title")
    public String subTitle;


    @ColumnInfo(name ="note_text")
    public String noteText;

    public Note(String title, String subTitle, String noteText) {
        this.title = title;
        this.subTitle = subTitle;
        this.noteText = noteText;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }


    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }


    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;

        Note note = (Note) o;

        if (note_id != note.note_id) return false;
        return title != null ? title.equals(note.title) : note.title == null;
    }



    @Override
    public int hashCode() {
        int result = note_id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + note_id +
                ", title='" + title + '\'' +
                ", sub_title='" + subTitle + '\'' +
                ", note_text='" + noteText + '\'' +
                '}';
    }
}
