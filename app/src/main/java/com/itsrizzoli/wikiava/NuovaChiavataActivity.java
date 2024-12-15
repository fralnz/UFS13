package com.itsrizzoli.wikiava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.itsrizzoli.wikiava.models.Chiavata;
import com.itsrizzoli.wikiava.models.DataList;
import com.itsrizzoli.wikiava.models.Persona;
import com.itsrizzoli.wikiava.database.PersonaDbAdapter;
import com.itsrizzoli.wikiava.database.ChiavataDbAdapter;

import java.util.ArrayList;

public class NuovaChiavataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.nuova_chiavata);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.chiavaPagina), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText nomeEditText = findViewById(R.id.nome);
        EditText luogoEditText = findViewById(R.id.luogo);
        MultiAutoCompleteTextView selezionaTag = findViewById(R.id.selezionaTag);
        EditText dataEditText = findViewById(R.id.data);
        RatingBar votoBar = findViewById(R.id.ratingExperience);

        ArrayList<String> tags = DataList.getInstance().getTags();
        ArrayAdapter<String> tagsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                tags);
        selezionaTag.setAdapter(tagsAdapter);
        selezionaTag.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        Button salvaButton = findViewById(R.id.salvaButton);

        salvaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect data from input fields
                String nome = nomeEditText.getText().toString();
                String luogo = luogoEditText.getText().toString();
                String data = dataEditText.getText().toString();
                float voto = votoBar.getRating();

                // Initialize tags
                ArrayList<String> selectedTags = new ArrayList<>();
                for (String tag : selezionaTag.getText().toString().split(",\\s*")) {
                    selectedTags.add(tag.trim());
                }

                // Create a Persona object
                Persona persona = new Persona(nome, "Unknown"); // Example with placeholder genre

                // Insert Persona into database
                PersonaDbAdapter personaDbAdapter = new PersonaDbAdapter(NuovaChiavataActivity.this);
                personaDbAdapter.open();
                long personaId = personaDbAdapter.createPersona(persona);
                personaDbAdapter.close();

                if (personaId > 0) {
                    persona.setId((int) personaId);

                    // Create a Chiavata object
                    Chiavata nuovaChiavata = new Chiavata(persona, voto, luogo, luogo, data, "", selectedTags, 6, 33, 6);

                    // Insert Chiavata into database
                    ChiavataDbAdapter chiavataDbAdapter = new ChiavataDbAdapter(NuovaChiavataActivity.this);
                    chiavataDbAdapter.open();
                    chiavataDbAdapter.createChiavata(nuovaChiavata);
                    chiavataDbAdapter.close();
                }

                // Navigate back to HomeActivity
                Intent homeIntent = new Intent(NuovaChiavataActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}
