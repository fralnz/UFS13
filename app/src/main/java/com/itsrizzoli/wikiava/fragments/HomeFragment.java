package com.itsrizzoli.wikiava.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.itsrizzoli.wikiava.R;
import com.itsrizzoli.wikiava.adapters.ChiavataAdapter;
import com.itsrizzoli.wikiava.database.ChiavataDbAdapter;
import com.itsrizzoli.wikiava.models.Chiavata;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button chiavaButton = view.findViewById(R.id.chiavaButton);
        Button personeButton = view.findViewById(R.id.personeButton);

        chiavaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ricettaIntent = new Intent(getActivity(), NuovaChiavataFragment.class);
                startActivity(ricettaIntent);
            }
        });

        personeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ricettaIntent = new Intent(getActivity(), PersoneFragment.class);
                startActivity(ricettaIntent);
            }
        });

        ListView listView = view.findViewById(R.id.chiavateListView);

        ChiavataDbAdapter repository = new ChiavataDbAdapter(getActivity());
        ArrayList<Chiavata> chiavate = repository.fetchAllChiavateList();

        ChiavataAdapter chiavateAdapter = new ChiavataAdapter(getActivity(), chiavate);
        listView.setAdapter(chiavateAdapter);

        return view;
    }
}