package com.itsrizzoli.wikiava.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.itsrizzoli.wikiava.models.Persona;

import java.util.ArrayList;
import java.util.List;

public class PersonaDbAdapter {

    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    // Database Table and Columns
    private static final String TABLE_PERSONA = "Persone";
    private static final String KEY_ID = "ID_Persona";
    private static final String KEY_NOME = "nome";
    private static final String KEY_USATO_PROTEZIONI = "usato_protezioni";

    public PersonaDbAdapter(Context context) {
        this.context = context;
    }

    public PersonaDbAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(Persona persona) {
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, persona.getNome());
        return values;
    }

    public long createPersona(Persona persona) {
        ContentValues values = createContentValues(persona);
        return database.insertOrThrow(TABLE_PERSONA, null, values);
    }

    public boolean updatePersona(long id, Persona persona) {
        ContentValues values = createContentValues(persona);
        return database.update(TABLE_PERSONA, values, KEY_ID + "=" + id, null) > 0;
    }

    public boolean deletePersona(long id) {
        return database.delete(TABLE_PERSONA, KEY_ID + "=" + id, null) > 0;
    }

    public Cursor fetchAllPersone() {
        return database.query(TABLE_PERSONA,
                new String[]{"ID_Persona AS _id", "nome", "genere"},
                null,
                null,
                null,
                null,
                null
        );
    }

    public Persona getPersonaByName(String nome) {
        Persona persona = null;
        Cursor cursor = database.query(TABLE_PERSONA,
                new String[]{"ID_Persona", "nome", "genere"},
                "nome = ?",
                new String[]{nome},
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID_Persona"));
            String genere = cursor.getString(cursor.getColumnIndex("genere"));
            persona = new Persona(nome, genere);
            persona.setId(id);
            cursor.close();
        }
        return persona;
    }

    public Cursor fetchPersonaById(long id) {
        Cursor cursor = database.query(TABLE_PERSONA, null, KEY_ID + "=" + id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public List<String> getAllPersoneNames() {
        List<String> namesList = new ArrayList<>();
        Cursor cursor = database.query(TABLE_PERSONA,
                new String[]{KEY_NOME},
                null,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int nameColumnIndex = cursor.getColumnIndex(KEY_NOME);
                String name = cursor.getString(nameColumnIndex);
                namesList.add(name);
            }
            cursor.close();
        }
        return namesList;
    }
}
