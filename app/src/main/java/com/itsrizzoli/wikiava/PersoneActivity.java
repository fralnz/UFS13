package com.itsrizzoli.wikiava;

import android.os.Bundle;
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

import com.itsrizzoli.wikiava.models.DataList;
import com.itsrizzoli.wikiava.models.Persona;

import java.util.ArrayList;

public class PersoneActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_persone);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.personePagina), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<Persona> persone = DataList.getInstance().getPersone();
        ListView listView = findViewById(R.id.personeListView);
        ArrayAdapter<Persona> personeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, persone);
        listView.setAdapter(personeAdapter);
    }
}
