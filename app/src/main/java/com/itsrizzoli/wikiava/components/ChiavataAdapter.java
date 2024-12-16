package com.itsrizzoli.wikiava.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itsrizzoli.wikiava.R;
import com.itsrizzoli.wikiava.models.Chiavata;

import java.util.List;

public class ChiavataAdapter extends ArrayAdapter<Chiavata> {
    private Context context;
    private List<Chiavata> chiavate;

    private static class ViewHolder {
        TextView personaTextView;
        TextView luogoTextView;
        TextView postoTextView;
        TextView dataTextView;
        TextView votoTextView;
        TextView descrizioneTextView;
    }

    public ChiavataAdapter(Context context, List<Chiavata> chiavate) {
        super(context, -1, chiavate);
        this.context = context;
        this.chiavate = chiavate;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chiavata_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.personaTextView = convertView.findViewById(R.id.personaTextView);
            viewHolder.luogoTextView = convertView.findViewById(R.id.luogoTextView);
            viewHolder.postoTextView = convertView.findViewById(R.id.postoTextView);
            viewHolder.dataTextView = convertView.findViewById(R.id.dataTextView);
            viewHolder.votoTextView = convertView.findViewById(R.id.votoTextView);
            viewHolder.descrizioneTextView = convertView.findViewById(R.id.descrizioneTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Chiavata chiavata = chiavate.get(position);

        // controllo se la persona esiste (puo dare problemi ogni tanto)
        viewHolder.personaTextView.setText(
                chiavata.getPersona() != null ? chiavata.getPersona().getNome() : "N/A"
        );
        viewHolder.luogoTextView.setText("Luogo: " + chiavata.getLuogo());
        viewHolder.postoTextView.setText("Posto: " + chiavata.getPosto());
        viewHolder.dataTextView.setText("Data: " + chiavata.getData());
        viewHolder.votoTextView.setText("Voto: " + String.format("%.1f", chiavata.getVoto()));
        viewHolder.descrizioneTextView.setText("Descrizione: " + chiavata.getDescrizione());

        return convertView;
    }
}