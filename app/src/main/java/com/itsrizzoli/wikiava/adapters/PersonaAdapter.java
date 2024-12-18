package com.itsrizzoli.wikiava.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.itsrizzoli.wikiava.R;
import com.itsrizzoli.wikiava.models.DataList;

import java.util.Objects;

public class PersonaAdapter extends CursorAdapter {
    DataList dataList = DataList.getInstance();

    public PersonaAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.persona_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Debugging logs
        int nomeIndex = cursor.getColumnIndex("nome");
        int genereIndex = cursor.getColumnIndex("genere");

        if (nomeIndex >= 0) {
            Log.d("PersonaAdapter", "nome: " + cursor.getString(nomeIndex));
        } else {
            Log.e("PersonaAdapter", "Column 'nome' not found!");
        }

        // Safely retrieve ID
        int id = cursor.getInt(cursor.getColumnIndex("_id"));

        // Safely retrieve Body Count
        String bodyCount = dataList.getBodyCountMap() != null && dataList.getBodyCountMap().containsKey(id)
                ? dataList.getBodyCountMap().getOrDefault(id, 0).toString()
                : "0";

        // Safely retrieve Nome
        String nome = cursor.isNull(nomeIndex) ? "Unknown" : cursor.getString(nomeIndex);

        // Bind data
        TextView nomeTextView = view.findViewById(R.id.nomeTextView);
        TextView bodyCountTextView = view.findViewById(R.id.bodyCountTextView);

        nomeTextView.setText(nome);
        bodyCountTextView.setText(bodyCount);
    }
}