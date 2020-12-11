package com.example.e_pertanian.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.e_pertanian.R;
import com.example.e_pertanian.note.AddNoteActivity;
import com.example.e_pertanian.note.Note;
import com.example.e_pertanian.note.NoteAdapter;
import com.example.e_pertanian.note.NoteDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Notes extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private ArrayList<Note> mNotesData;
    private NoteAdapter mAdapter;
    private NoteDatabase noteDatabase;
    private List<Note> notes;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        initializeVies();
        displayList();

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mNotesData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new NoteAdapter(this, mNotesData);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //variabel untuk menangkap posisi awal
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mNotesData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                noteDatabase.getNoteDao().delete(notes.get(pos));
                notes.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyDataSetChanged();
                Toast.makeText(Notes.this, "Deleting ", Toast.LENGTH_LONG).show();
                //mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        });

        helper.attachToRecyclerView(mRecyclerView);

    }

    private void displayList(){
        //initialize database instance
        noteDatabase = NoteDatabase.getInstance(Notes.this);
        //fetch list of notes in background thread
        new RetrieveTask(this).execute();

    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//    }


    private static class RetrieveTask extends AsyncTask<Void,Void,List<Note>> {

        private WeakReference<Notes> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(Notes context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().noteDatabase.getNoteDao().getAll();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            if (notes!=null && notes.size()>0 ){
                activityReference.get().notes = notes;

                // hides empty text view
                //activityReference.get().textViewMsg.setVisibility(View.GONE);

                // create and set the adapter on RecyclerView instance to display list
                activityReference.get().mAdapter = new NoteAdapter(activityReference.get(), notes);
                activityReference.get().mRecyclerView.setAdapter(activityReference.get().mAdapter);
            }
        }

    }

    private void initializeVies(){

        // Action button to add note
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBtnCreateNote);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Notes.this));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToMain = new Intent(Notes.this, AddNoteActivity.class);
                startActivity(intToMain);
            }
        });

    }

}