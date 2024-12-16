package com.itsrizzoli.wikiava.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.itsrizzoli.wikiava.models.Chiavata;
import com.itsrizzoli.wikiava.models.Persona;

import java.util.ArrayList;

public class ChiavataDbAdapter {

    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    // Database Table and Columns
    private static final String TABLE_CHIAVATA = "Rapporti";
    private static final String KEY_ID = "ID_Rapporti";
    private static final String KEY_PERSONA_ID = "ID_Persona";
    private static final String KEY_VOTO = "votazione";
    private static final String KEY_LUOGO = "luogo";
    private static final String KEY_POSTO = "posto";
    private static final String KEY_DATA = "data_rapporto";
    private static final String KEY_DESCRIZIONE = "descrizione";
    private static final String KEY_TAGS_ID = "ID_Tags";

    public ChiavataDbAdapter(Context context) {
        this.context = context;
    }

    public ChiavataDbAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(Chiavata chiavata) {
        ContentValues values = new ContentValues();
        values.put(KEY_PERSONA_ID, chiavata.getPersona().getId());
        values.put(KEY_VOTO, chiavata.getVoto());
        values.put(KEY_LUOGO, chiavata.getLuogo());
        values.put(KEY_POSTO, chiavata.getPosto());
        values.put(KEY_DATA, chiavata.getData());
        values.put(KEY_DESCRIZIONE, chiavata.getDescrizione());
        // Assuming tags are stored separately and linked via ID_TAGS
        return values;
    }

    public long createChiavata(Chiavata chiavata) {
        ContentValues values = createContentValues(chiavata);
        return database.insertOrThrow(TABLE_CHIAVATA, null, values);
    }

    public boolean updateChiavata(long id, Chiavata chiavata) {
        ContentValues values = createContentValues(chiavata);
        return database.update(TABLE_CHIAVATA, values, KEY_ID + "=" + id, null) > 0;
    }

    public boolean deleteChiavata(long id) {
        return database.delete(TABLE_CHIAVATA, KEY_ID + "=" + id, null) > 0;
    }

    public Cursor fetchAllChiavate() {
        return database.query(TABLE_CHIAVATA,
                new String[] {KEY_ID, KEY_PERSONA_ID, KEY_VOTO, KEY_LUOGO, KEY_POSTO, KEY_DATA, KEY_DESCRIZIONE},
                null, null, null, null, null);
    }

    public Cursor fetchChiavataById(long id) {
        Cursor cursor = database.query(TABLE_CHIAVATA, null, KEY_ID + "=" + id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public ArrayList<Chiavata> fetchAllChiavateList() {
        ArrayList<Chiavata> chiavate = new ArrayList<>();

        ChiavataDbAdapter chiavataDbAdapter = new ChiavataDbAdapter(context);
        PersonaDbAdapter personaDbAdapter = new PersonaDbAdapter(context);

        chiavataDbAdapter.open();
        personaDbAdapter.open();

        Cursor chiavataCursor = chiavataDbAdapter.fetchAllChiavate();

        if (chiavataCursor != null && chiavataCursor.moveToFirst()) {
            int idIndex = chiavataCursor.getColumnIndex("ID_Rapporti");
            int votoIndex = chiavataCursor.getColumnIndex("votazione");
            int luogoIndex = chiavataCursor.getColumnIndex("luogo");
            int postoIndex = chiavataCursor.getColumnIndex("posto");
            int dataIndex = chiavataCursor.getColumnIndex("data_rapporto");
            int descrizioneIndex = chiavataCursor.getColumnIndex("descrizione");
            int personaIdIndex = chiavataCursor.getColumnIndex("ID_Persona");

            do {
                Chiavata chiavata = new Chiavata();

                if (idIndex >= 0) chiavata.setId(chiavataCursor.getInt(idIndex));
                if (votoIndex >= 0) chiavata.setVoto(chiavataCursor.getFloat(votoIndex));
                if (luogoIndex >= 0) chiavata.setLuogo(chiavataCursor.getString(luogoIndex));
                if (postoIndex >= 0) chiavata.setPosto(chiavataCursor.getString(postoIndex));
                if (dataIndex >= 0) chiavata.setData(chiavataCursor.getString(dataIndex));
                if (descrizioneIndex >= 0) chiavata.setDescrizione(chiavataCursor.getString(descrizioneIndex));

                // Fetch Persona associated with the Chiavata
                if (personaIdIndex >= 0) {
                    int personaId = chiavataCursor.getInt(personaIdIndex);
                    Cursor personaCursor = personaDbAdapter.fetchPersonaById(personaId);
                    if (personaCursor != null && personaCursor.moveToFirst()) {
                        Persona persona = new Persona(
                                personaCursor.getString(personaCursor.getColumnIndex("nome")),
                                personaCursor.getString(personaCursor.getColumnIndex("genere"))
                        );
                        persona.setId(personaId);
                        chiavata.setPersona(persona);
                        personaCursor.close();
                    }
                }

                chiavate.add(chiavata);
            } while (chiavataCursor.moveToNext());
        }

        if (chiavataCursor != null) chiavataCursor.close();
        personaDbAdapter.close();
        chiavataDbAdapter.close();

        return chiavate;
    }
}