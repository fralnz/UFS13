package com.itsrizzoli.wikiava;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.itsrizzoli.wikiava.components.PersonaAdapter;
import com.itsrizzoli.wikiava.database.PersonaDbAdapter;
import com.itsrizzoli.wikiava.models.DataList;
import com.itsrizzoli.wikiava.models.Persona;

import java.util.ArrayList;

public class PersoneActivity extends AppCompatActivity {
    PersonaDbAdapter personaDbAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_persone);

        personaDbAdapter = new PersonaDbAdapter(getApplicationContext());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.personePagina), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.e("debug","prima dell'if");

        personaDbAdapter.open();

        Cursor personeCursor = personaDbAdapter.fetchAllPersone();
        ListView listView = findViewById(R.id.personeListView);

        if (personeCursor != null && personeCursor.getCount() > 0) {
            PersonaAdapter personeAdapter = new PersonaAdapter(PersoneActivity.this, personeCursor);
            listView.setAdapter(personeAdapter);
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        personaDbAdapter.close();
    }

}
