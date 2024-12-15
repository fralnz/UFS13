package com.itsrizzoli.wikiava.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.itsrizzoli.wikiava.models.Chiavata;
import com.itsrizzoli.wikiava.models.Persona;

import java.util.ArrayList;
import java.util.List;

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
}