package com.example.e_pertanian.note;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_pertanian.R;
import com.example.e_pertanian.note.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    // Member variables.
    private List<Note> mNotesData;
    private Context mContext;


    NoteAdapter(Context context, List<Note> notesData) {
        this.mNotesData = notesData;
        this.mContext = context;
    }


    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_note, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder,
                                 int position) {
        // Get current note.
        Note currentNote = mNotesData.get(position);
        //holder.bindTo(currentNote);
        holder.bindTo(currentNote);
    }


    @Override
    public int getItemCount() {
        return mNotesData.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mSubTitleText;
        private TextView mDescriptionText;


        ViewHolder(View itemView) {
            super(itemView);
            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mSubTitleText = itemView.findViewById(R.id.subTitle);
            mDescriptionText = itemView.findViewById(R.id.description);
        }

        void bindTo(Note currentNote){
            // Populate the textviews with data.
            mTitleText.setText(currentNote.getTitle());
            mSubTitleText.setText(currentNote.getSubTitle());
            mDescriptionText.setText(currentNote.getNoteText());
            itemView.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "Clicked", Toast.LENGTH_LONG).show();
        }
    }

//    interface OnItemClickListener{
//        void onItemClicked(Note note);
//    }
}
