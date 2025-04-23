package com.skivadev.misnotas;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Nota> notes = new ArrayList<>();
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Mis Notas");
        }

        // Configurar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.notes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NotesAdapter(notes, position -> {
            // Abrir nota para editar
            Intent intent = new Intent(MainActivity.this, NotaActivity.class);
            intent.putExtra("note_title", notes.get(position).getTitle());
            intent.putExtra("note_content", notes.get(position).getContent());
            intent.putExtra("note_position", position);
            startActivityForResult(intent, 1);
        });
        recyclerView.setAdapter(adapter);

        // Configurar FloatingActionButton
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Abrir nueva nota
            Intent intent = new Intent(MainActivity.this, NotaActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("note_title");
            String content = data.getStringExtra("note_content");
            int position = data.getIntExtra("note_position", -1);

            if (position != -1) {
                // Actualizar nota existente
                notes.set(position, new Nota(title, content));
            } else {
                // Agregar nueva nota
                notes.add(0, new Nota(title, content)); // Agrega al inicio
            }
            adapter.notifyDataSetChanged();
        }
    }
}