package com.example.e_pertanian.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_pertanian.R;

import java.lang.ref.WeakReference;

public class AddNoteActivity extends AppCompatActivity {

    private EditText title, subTitle, description;
    private ImageView done;
    private NoteDatabase noteDatabase;
    private Note note;
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title = findViewById(R.id.etNoteTitle);
        subTitle = findViewById(R.id.etNoteSubTitle);
        description = findViewById(R.id.etNoteDescription);

        noteDatabase = NoteDatabase.getInstance(AddNoteActivity.this);
        done = findViewById(R.id.imgDone);

        if ( (note = (Note) getIntent().getSerializableExtra("note"))!=null ){
            update = true;
            title.setText(note.getTitle());
            subTitle.setText(note.getSubTitle());
            description.setText(note.getNoteText());

            done.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view)
                {

                    if(TextUtils.isEmpty(title.getText())){
                        Toast.makeText(AddNoteActivity.this, "Title is required", Toast.LENGTH_SHORT).show();
                    }
                    if(TextUtils.isEmpty(subTitle.getText())){
                        Toast.makeText(AddNoteActivity.this, "Sub title is required", Toast.LENGTH_SHORT).show();
                    }
                    if(TextUtils.isEmpty(description.getText())){
                        Toast.makeText(AddNoteActivity.this, "Description is required", Toast.LENGTH_SHORT).show();
                    }

                    note.setTitle(title.getText().toString());
                    note.setSubTitle(subTitle.getText().toString());
                    note.setNoteText(description.getText().toString());

                    noteDatabase.getNoteDao().update(note);
                }
            });
        } else {
            done.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view)
                {

                    if(TextUtils.isEmpty(title.getText())){
                        Toast.makeText(AddNoteActivity.this, "Title is required", Toast.LENGTH_SHORT).show();
                    }
                    if(TextUtils.isEmpty(subTitle.getText())){
                        Toast.makeText(AddNoteActivity.this, "Sub title is required", Toast.LENGTH_SHORT).show();
                    }
                    if(TextUtils.isEmpty(description.getText())){
                        Toast.makeText(AddNoteActivity.this, "Description is required", Toast.LENGTH_SHORT).show();
                    }

                    note = new Note(title.getText().toString(), subTitle.getText().toString(), description.getText().toString());

                    // create worker thread to insert data into database
                    new InsertTask(AddNoteActivity.this,note).execute();
                }
            });
        }

    }

    private void setResult(Note note, int flag){
        setResult(flag,new Intent().putExtra("note", (Parcelable) note));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddNoteActivity> activityReference;
        private Note note;

        // only retain a weak reference to the activity
        InsertTask(AddNoteActivity context, Note note) {
            activityReference = new WeakReference<>(context);
            this.note = note;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().noteDatabase.getNoteDao().insert(note);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(note,1);
            }
        }

    }
}