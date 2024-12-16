package com.itsrizzoli.wikiava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.itsrizzoli.wikiava.components.ChiavataAdapter;
import com.itsrizzoli.wikiava.database.ChiavataDbAdapter;
import com.itsrizzoli.wikiava.models.Chiavata;

import java.util.ArrayList;

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

        ChiavataDbAdapter repository = new ChiavataDbAdapter(this);
        ArrayList<Chiavata> chiavate = repository.fetchAllChiavateList();

        ChiavataAdapter chiavateAdapter = new ChiavataAdapter(this, chiavate);
        listView.setAdapter(chiavateAdapter);
    }
}