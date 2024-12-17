package com.itsrizzoli.wikiava.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.itsrizzoli.wikiava.R;
import com.itsrizzoli.wikiava.adapters.PersonaAdapter;
import com.itsrizzoli.wikiava.database.PersonaDbAdapter;

public class PersoneFragment extends Fragment {
    private PersonaDbAdapter personaDbAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persone, container, false);

        personaDbAdapter = new PersonaDbAdapter(getActivity());

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.personePagina), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.e("debug", "prima dell'if");

        personaDbAdapter.open();

        Cursor personeCursor = personaDbAdapter.fetchAllPersone();
        ListView listView = view.findViewById(R.id.personeListView);

        if (personeCursor != null && personeCursor.getCount() > 0) {
            PersonaAdapter personeAdapter = new PersonaAdapter(getActivity(), personeCursor);
            listView.setAdapter(personeAdapter);
        } else {
            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        personaDbAdapter.close();
    }
}