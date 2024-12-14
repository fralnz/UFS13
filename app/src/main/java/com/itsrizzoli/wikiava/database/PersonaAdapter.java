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

public class PersonaAdapter {
    private static final String LOG_TAG = PersonaAdapter.class.getSimpleName();

    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    // Table name and columns for Persone
    private static final String DATABASE_TABLE_PERSONE = "Persone";
    private static final String KEY_ID_PERSONA = "id_persona";
    private static final String KEY_NOME = "nome";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_GENERE = "genere";

    // Table name for Rapporti and Rapporti_Persone
    private static final String DATABASE_TABLE_RAPPORTI = "Rapporti";
    private static final String DATABASE_TABLE_RAPPORTI_PERSONE = "Rapporti_Persone";
    private static final String KEY_ID_RAPPORTO = "id_rapporto";

    public PersonaAdapter(Context context) {
        this.context = context;
    }

    public PersonaAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Insert a new Persona into the database
     * @param persona The Persona object to insert
     * @return the row ID of the newly inserted Persona
     */
    public long insertPersona(Persona persona) {
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, persona.getNome());
        values.put(KEY_GENERE, persona.getGenere());

        return database.insert(DATABASE_TABLE_PERSONE, null, values);
    }

    /**
     * Update an existing Persona in the database
     * @param persona The Persona object to update
     * @return number of rows affected
     */
    public int updatePersona(Persona persona) {
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, persona.getNome());
        values.put(KEY_GENERE, persona.getGenere());

        return database.update(DATABASE_TABLE_PERSONE,
                values,
                KEY_NOME + " = ?",
                new String[]{persona.getNome()}
        );
    }

    /**
     * Retrieve a Persona by name
     * @param nome Name of the Persona
     * @return Persona object or null if not found
     */
    public Persona getPersonaByNome(String nome) {
        Cursor cursor = null;
        Persona persona = null;
        try {
            cursor = database.query(DATABASE_TABLE_PERSONE,
                    null,
                    KEY_NOME + " = ?",
                    new String[]{nome},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                // Safely get column indices
                int nomeIndex = cursor.getColumnIndex(KEY_NOME);
                int genereIndex = cursor.getColumnIndex(KEY_GENERE);

                if (nomeIndex != -1 && genereIndex != -1) {
                    persona = new Persona(
                            cursor.getString(nomeIndex),
                            cursor.getString(genereIndex)
                    );
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error retrieving Persona", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return persona;
    }

    /**
     * Retrieve all Persone
     * @return List of Persona objects
     */
    public List<Persona> getAllPersone() {
        List<Persona> persone = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.query(DATABASE_TABLE_PERSONE,
                    null, null, null, null, null, KEY_NOME);

            if (cursor != null) {
                // Safely get column indices
                int nomeIndex = cursor.getColumnIndex(KEY_NOME);
                int genereIndex = cursor.getColumnIndex(KEY_GENERE);

                if (nomeIndex != -1 && genereIndex != -1) {
                    while (cursor.moveToNext()) {
                        Persona persona = new Persona(
                                cursor.getString(nomeIndex),
                                cursor.getString(genereIndex)
                        );

                        persone.add(persona);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error retrieving all Persone", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return persone;
    }

    /**
     * Get body count for a specific Persona
     * @param persona Persona object
     * @return number of Chiavate with this Persona
     */
    private int getBodyCountForPersona(Persona persona) {
        Cursor cursor = null;
        int bodyCount = 0;
        try {
            cursor = database.rawQuery(
                    "SELECT COUNT(*) AS body_count FROM Rapporti r " +
                            "JOIN Rapporti_Persone rp ON r.id_rapporto = rp.id_rapporto " +
                            "JOIN Persone p ON rp.id_persona = p.id_persona " +
                            "WHERE p.nome = ?",
                    new String[]{persona.getNome()}
            );

            if (cursor != null && cursor.moveToFirst()) {
                int bodyCountIndex = cursor.getColumnIndex("body_count");
                if (bodyCountIndex != -1) {
                    bodyCount = cursor.getInt(bodyCountIndex);
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error getting body count", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return bodyCount;
    }

    /**
     * Delete a Persona by name
     * @param nome Name of the Persona to delete
     * @return number of rows affected
     */
    public int deletePersona(String nome) {
        return database.delete(DATABASE_TABLE_PERSONE,
                KEY_NOME + " = ?",
                new String[]{nome}
        );
    }

    /**
     * Check if a Persona exists
     * @param nome Name of the Persona
     * @return true if Persona exists, false otherwise
     */
    public boolean personaExists(String nome) {
        Cursor cursor = null;
        boolean exists = false;
        try {
            cursor = database.query(DATABASE_TABLE_PERSONE,
                    new String[]{KEY_NOME},
                    KEY_NOME + " = ?",
                    new String[]{nome},
                    null, null, null);

            exists = cursor != null && cursor.moveToFirst();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error checking Persona existence", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return exists;
    }
}