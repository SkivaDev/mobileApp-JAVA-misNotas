package com.skivadev.misnotas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<Nota> notesList;
    private OnNoteClickListener listener;

    public interface OnNoteClickListener {
        void onNoteClick(int position);
    }

    public NotesAdapter(List<Nota> notesList, OnNoteClickListener listener) {
        this.notesList = notesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Nota note = notesList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.contentTextView.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;

        public NoteViewHolder(@NonNull View itemView, final OnNoteClickListener listener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.note_title);
            contentTextView = itemView.findViewById(R.id.note_content);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onNoteClick(position);
                    }
                }
            });
        }
    }
}
