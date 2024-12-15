package com.itsrizzoli.wikiava;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.itsrizzoli.wikiava.database.ChiavataDbAdapter;
import com.itsrizzoli.wikiava.models.Chiavata;
import com.itsrizzoli.wikiava.models.DataList;

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
        ChiavataDbAdapter dbAdapter = new ChiavataDbAdapter(getApplicationContext());
        dbAdapter.open();

        Cursor cursor = dbAdapter.fetchAllChiavate();
        ArrayList<Chiavata> chiavate = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Chiavata chiavata = new Chiavata();
                chiavata.setId(cursor.getInt(cursor.getColumnIndex("ID_Rapporti")));
                chiavata.setVoto(cursor.getFloat(cursor.getColumnIndex("votazione")));
                chiavata.setLuogo(cursor.getString(cursor.getColumnIndex("luogo")));
                chiavata.setPosto(cursor.getString(cursor.getColumnIndex("posto")));
                chiavata.setData(cursor.getString(cursor.getColumnIndex("data_rapporto")));
                chiavata.setDescrizione(cursor.getString(cursor.getColumnIndex("descrizione")));
                // Assuming you handle tags separately or load them later
                chiavate.add(chiavata);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        dbAdapter.close();

        ArrayAdapter<Chiavata> chiavateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chiavate);
        listView.setAdapter(chiavateAdapter);
    }
}