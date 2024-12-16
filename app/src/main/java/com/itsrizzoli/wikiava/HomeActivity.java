package com.itsrizzoli.wikiava;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.itsrizzoli.wikiava.components.ChiavataAdapter;
import com.itsrizzoli.wikiava.database.ChiavataDbAdapter;
import com.itsrizzoli.wikiava.database.PersonaDbAdapter;
import com.itsrizzoli.wikiava.models.Chiavata;
import com.itsrizzoli.wikiava.models.DataList;
import com.itsrizzoli.wikiava.models.Persona;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button chiavaButton = findViewById(R.id.chiavaButton);
        Button personeButton = findViewById(R.id.personeButton);

        chiavaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ricettaIntent = new Intent(HomeActivity.this, NuovaChiavataActivity.class);
                startActivity(ricettaIntent);
            }
        });

        personeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ricettaIntent = new Intent(HomeActivity.this, PersoneActivity.class);
                startActivity(ricettaIntent);
            }
        });

        ListView listView = findViewById(R.id.chiavateListView);
        // Initialize database adapters
        ChiavataDbAdapter chiavataDbAdapter = new ChiavataDbAdapter(this);
        PersonaDbAdapter personaDbAdapter = new PersonaDbAdapter(this);

// Open database connections
        chiavataDbAdapter.open();
        personaDbAdapter.open();

// Fetch all Chiavata records
        Cursor chiavataCursor = chiavataDbAdapter.fetchAllChiavate();
        ArrayList<Chiavata> chiavate = new ArrayList<>();

        if (chiavataCursor != null && chiavataCursor.moveToFirst()) {
            int idIndex = chiavataCursor.getColumnIndex("ID_Rapporti");
            int votoIndex = chiavataCursor.getColumnIndex("votazione");
            int luogoIndex = chiavataCursor.getColumnIndex("luogo");
            int postoIndex = chiavataCursor.getColumnIndex("posto");
            int dataIndex = chiavataCursor.getColumnIndex("data_rapporto");
            int descrizioneIndex = chiavataCursor.getColumnIndex("descrizione");
            int personaIdIndex = chiavataCursor.getColumnIndex("ID_Persona");

            do {
                Chiavata chiavata = new Chiavata();

                if (idIndex >= 0) chiavata.setId(chiavataCursor.getInt(idIndex));
                if (votoIndex >= 0) chiavata.setVoto(chiavataCursor.getFloat(votoIndex));
                if (luogoIndex >= 0) chiavata.setLuogo(chiavataCursor.getString(luogoIndex));
                if (postoIndex >= 0) chiavata.setPosto(chiavataCursor.getString(postoIndex));
                if (dataIndex >= 0) chiavata.setData(chiavataCursor.getString(dataIndex));
                if (descrizioneIndex >= 0)
                    chiavata.setDescrizione(chiavataCursor.getString(descrizioneIndex));

                // Fetch Persona associated with the Chiavata
                if (personaIdIndex >= 0) {
                    int personaId = chiavataCursor.getInt(personaIdIndex);
                    Cursor personaCursor = personaDbAdapter.fetchPersonaById(personaId);
                    if (personaCursor != null && personaCursor.moveToFirst()) {
                        Persona persona = new Persona(
                                personaCursor.getString(personaCursor.getColumnIndex("nome")),
                                personaCursor.getString(personaCursor.getColumnIndex("genere"))
                        );
                        persona.setId(personaId);
                        chiavata.setPersona(persona);
                        personaCursor.close();
                    }
                }

                chiavate.add(chiavata);
            } while (chiavataCursor.moveToNext());
        }

        if (chiavataCursor != null) {
            chiavataCursor.close();
        }
        personaDbAdapter.close();
        chiavataDbAdapter.close();

        ChiavataAdapter chiavateAdapter = new ChiavataAdapter(this, chiavate);
        listView.setAdapter(chiavateAdapter);

    }
}