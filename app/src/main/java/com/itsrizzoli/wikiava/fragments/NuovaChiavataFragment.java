package com.itsrizzoli.wikiava.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.itsrizzoli.wikiava.MainActivity;
import com.itsrizzoli.wikiava.R;
import com.itsrizzoli.wikiava.models.Chiavata;
import com.itsrizzoli.wikiava.models.DataList;
import com.itsrizzoli.wikiava.models.Persona;
import com.itsrizzoli.wikiava.database.PersonaDbAdapter;
import com.itsrizzoli.wikiava.database.ChiavataDbAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class NuovaChiavataFragment extends Fragment {
    private EditText dateEditText;
    private TabLayout tabLayout;
    private Button changeTabButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nuova_chiavata, container, false);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.chiavaPagina), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText nomeEditText = view.findViewById(R.id.nome);
        EditText luogoEditText = view.findViewById(R.id.luogo);
        MultiAutoCompleteTextView selezionaTag = view.findViewById(R.id.selezionaTag);
        dateEditText = view.findViewById(R.id.data);
        RatingBar votoBar = view.findViewById(R.id.ratingExperience);

        ArrayList<String> tags = DataList.getInstance().getTags();
        ArrayAdapter<String> tagsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, tags);
        selezionaTag.setAdapter(tagsAdapter);
        selezionaTag.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        Button salvaButton = view.findViewById(R.id.salvaButton);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        salvaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = nomeEditText.getText().toString();
                String luogo = luogoEditText.getText().toString();
                String data = dateEditText.getText().toString();
                float voto = votoBar.getRating();

                ArrayList<String> selectedTags = new ArrayList<>();
                for (String tag : selezionaTag.getText().toString().split(",\\s*")) {
                    selectedTags.add(tag.trim());
                }

                if (nome.isBlank() || luogo.isBlank() || data.isBlank()) {
                    Toast.makeText(getContext(), "Inserisci tutti i campi", Toast.LENGTH_SHORT).show();
                } else {
                    PersonaDbAdapter personaDbAdapter = new PersonaDbAdapter(getActivity());
                    personaDbAdapter.open();

                    // Check if a Persona with the given name already exists
                    Persona persona = personaDbAdapter.getPersonaByName(nome);
                    if (persona == null) {
                        persona = new Persona(nome, "Unknown");
                        long personaId = personaDbAdapter.createPersona(persona);
                        if (personaId > 0) {
                            persona.setId((int) personaId);
                        }
                    }
                    personaDbAdapter.close();

                    // Create and save Chiavata
                    Chiavata nuovaChiavata = new Chiavata(persona, voto, luogo, luogo, data, "", selectedTags, 6, 33, 6);
                    ChiavataDbAdapter chiavataDbAdapter = new ChiavataDbAdapter(getActivity());
                    chiavataDbAdapter.open();
                    chiavataDbAdapter.createChiavata(nuovaChiavata);
                    chiavataDbAdapter.close();

                    Toast.makeText(getContext(), "Chiavata Registrata!", Toast.LENGTH_SHORT).show();

                    // Switch to another tab
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).switchToTab(0); // Switch to tab at index 1
                    }
                }
            }
        });


        return view;
    }
}