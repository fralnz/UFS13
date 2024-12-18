package com.itsrizzoli.wikiava.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.itsrizzoli.wikiava.R;
import com.itsrizzoli.wikiava.models.DataList;

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
        // Get data from the cursor
        String nome = cursor.getString(cursor.getColumnIndex("nome"));
        String id = cursor.getString(cursor.getColumnIndex("_id"));
        String genere = dataList.getBodyCountMap().get(Integer.parseInt(id)).toString();

        // Find views in the layout
        TextView nomeTextView = view.findViewById(R.id.nomeTextView);
        TextView genereTextView = view.findViewById(R.id.genereTextView);

        // Bind data to the views
        nomeTextView.setText(nome);
        genereTextView.setText(genere);
    }
}
