package com.itsrizzoli.wikiava.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

        ListView listView = view.findViewById(R.id.chiavateListView);

        ChiavataDbAdapter repository = new ChiavataDbAdapter(getActivity());
        ArrayList<Chiavata> chiavate = repository.fetchAllChiavateList();

        ChiavataAdapter chiavateAdapter = new ChiavataAdapter(getActivity(), chiavate);
        listView.setAdapter(chiavateAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected Chiavata
                Chiavata selectedChiavata = chiavate.get(position);

                // Show confirmation dialog
                new AlertDialog.Builder(getActivity())
                        .setTitle("Conferma eliminazione")
                        .setMessage("Sei sicuro di voler eliminare questa chiavata?")
                        .setPositiveButton("Conferma", (dialog, which) -> {
                            // Delete Chiavata from database
                            ChiavataDbAdapter chiavataDbAdapter = new ChiavataDbAdapter(getActivity());
                            chiavataDbAdapter.open();
                            boolean success = chiavataDbAdapter.deleteChiavata(selectedChiavata.getId());
                            chiavataDbAdapter.close();

                            if (success) {
                                // Remove Chiavata from the list and update the adapter
                                chiavate.remove(position);
                                chiavateAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "Chiavata eliminata", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Errore durante l'eliminazione", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Annulla", null)
                        .show();

                return true; // Indicate that the long-press has been handled
            }
        });

        return view;
    }
}