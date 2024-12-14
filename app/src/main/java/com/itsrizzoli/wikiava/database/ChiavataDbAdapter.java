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
    private static final String LOG_TAG = ChiavataDbAdapter.class.getSimpleName();

    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    // Table name and columns for Rapporti (Chiavata)
    private static final String DATABASE_TABLE_RAPPORTI = "Rapporti";
    private static final String KEY_ID_RAPPORTO = "id_rapporto";
    private static final String KEY_DATA_RAPPORTO = "data_rapporto";
    private static final String KEY_TITOLO_EVENTO = "titolo_evento";
    private static final String KEY_VOTO = "voto";
    private static final String KEY_USATO_PROTEZIONE = "usato_protezione";
    private static final String KEY_DESCRIZIONE = "descrizione";
    private static final String KEY_LUOGO = "luogo";
    private static final String KEY_POSTO = "posto";
    private static final String KEY_NUM_O = "num_o";
    private static final String KEY_MINUTI = "minuti";
    private static final String KEY_NUM_ROUND = "num_round";

    // Table name and columns for Rapporti_Persone (to link Chiavata with Persona)
    private static final String DATABASE_TABLE_RAPPORTI_PERSONE = "Rapporti_Persone";
    private static final String KEY_ID_PERSONA = "id_persona";

    // Table name and columns for Rapporto_Tags
    private static final String DATABASE_TABLE_RAPPORTO_TAGS = "Rapporto_Tags";
    private static final String KEY_ID_TAG = "id_tag";

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

    /**
     * Insert a new Chiavata into the database
     * @param chiavata The Chiavata object to insert
     * @return the row ID of the newly inserted Chiavata
     */
    public long insertChiavata(Chiavata chiavata) {
        long rapportoId = -1;
        try {
            // Insert main Rapporti record
            ContentValues values = new ContentValues();
            values.put(KEY_DATA_RAPPORTO, chiavata.getData());
            values.put(KEY_TITOLO_EVENTO, "Chiavata");
            values.put(KEY_VOTO, chiavata.getVoto());
            values.put(KEY_USATO_PROTEZIONE, false);
            values.put(KEY_DESCRIZIONE, chiavata.getDescrizione());
            values.put(KEY_LUOGO, chiavata.getLuogo());
            values.put(KEY_POSTO, chiavata.getPosto());
            values.put(KEY_NUM_O, chiavata.getNumO());
            values.put(KEY_MINUTI, chiavata.getMinuti());
            values.put(KEY_NUM_ROUND, chiavata.getNumRound());

            // Insert Rapporti record and get its ID
            rapportoId = database.insert(DATABASE_TABLE_RAPPORTI, null, values);

            // Insert Rapporti_Persone record to link Rapporto with Persona
            if (rapportoId != -1 && chiavata.getPersona() != null) {
                ContentValues linkValues = new ContentValues();
                linkValues.put(KEY_ID_RAPPORTO, rapportoId);
                linkValues.put(KEY_ID_PERSONA, chiavata.getPersona().getId());
                database.insert(DATABASE_TABLE_RAPPORTI_PERSONE, null, linkValues);
            }

            // Insert Tags
            if (rapportoId != -1 && chiavata.getTags() != null) {
                insertTags(rapportoId, chiavata.getTags());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error inserting Chiavata", e);
            rapportoId = -1;
        }

        return rapportoId;
    }

    /**
     * Insert tags for a specific Rapporto
     * @param rapportoId ID of the Rapporto
     * @param tags List of tag names
     */
    private void insertTags(long rapportoId, ArrayList<String> tags) {
        try {
            for (String tagName : tags) {
                // First, check if tag exists
                long tagId = getOrCreateTag(tagName);

                // Link tag to Rapporto
                if (tagId != -1) {
                    ContentValues tagLinkValues = new ContentValues();
                    tagLinkValues.put(KEY_ID_RAPPORTO, rapportoId);
                    tagLinkValues.put(KEY_ID_TAG, tagId);
                    database.insert(DATABASE_TABLE_RAPPORTO_TAGS, null, tagLinkValues);
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error inserting tags", e);
        }
    }

    /**
     * Get existing tag ID or create a new tag
     * @param tagName Name of the tag
     * @return ID of the tag
     */
    private long getOrCreateTag(String tagName) {
        Cursor cursor = null;
        long tagId = -1;
        try {
            // Query to check if tag exists
            cursor = database.query("Tags",
                    new String[]{"id_tag"},
                    "nome_tag = ?",
                    new String[]{tagName},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                // Tag exists, return its ID
                int tagIdIndex = cursor.getColumnIndex("id_tag");
                if (tagIdIndex != -1) {
                    tagId = cursor.getLong(tagIdIndex);
                }
            } else {
                // Create new tag
                ContentValues tagValues = new ContentValues();
                tagValues.put("nome_tag", tagName);
                tagId = database.insert("Tags", null, tagValues);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error getting or creating tag", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return tagId;
    }

    /**
     * Retrieve all Chiavate
     * @return List of Chiavata objects
     */
    public List<Chiavata> getAllChiavate() {
        List<Chiavata> chiavate = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.query(DATABASE_TABLE_RAPPORTI,
                    null, null, null, null, null, KEY_DATA_RAPPORTO + " DESC");

            if (cursor != null) {
                // Safely get column indices
                int idIndex = cursor.getColumnIndex(KEY_ID_RAPPORTO);
                int votoIndex = cursor.getColumnIndex(KEY_VOTO);
                int luogoIndex = cursor.getColumnIndex(KEY_LUOGO);
                int postoIndex = cursor.getColumnIndex(KEY_POSTO);
                int dataIndex = cursor.getColumnIndex(KEY_DATA_RAPPORTO);
                int descrizioneIndex = cursor.getColumnIndex(KEY_DESCRIZIONE);
                int numOIndex = cursor.getColumnIndex(KEY_NUM_O);
                int minutiIndex = cursor.getColumnIndex(KEY_MINUTI);
                int numRoundIndex = cursor.getColumnIndex(KEY_NUM_ROUND);

                // Check that all indices are valid
                if (idIndex != -1 && votoIndex != -1 && luogoIndex != -1 &&
                        postoIndex != -1 && dataIndex != -1 && descrizioneIndex != -1 &&
                        numOIndex != -1 && minutiIndex != -1 && numRoundIndex != -1) {

                    while (cursor.moveToNext()) {
                        Chiavata chiavata = new Chiavata();
                        chiavata.setId(cursor.getInt(idIndex));
                        chiavata.setVoto(cursor.getFloat(votoIndex));
                        chiavata.setLuogo(cursor.getString(luogoIndex));
                        chiavata.setPosto(cursor.getString(postoIndex));
                        chiavata.setData(cursor.getString(dataIndex));
                        chiavata.setDescrizione(cursor.getString(descrizioneIndex));
                        chiavata.setNumO(cursor.getInt(numOIndex));
                        chiavata.setMinuti(cursor.getInt(minutiIndex));
                        chiavata.setNumRound(cursor.getInt(numRoundIndex));

                        // Retrieve associated tags
                        chiavata.setTags(getTagsForChiavata(chiavata.getId()));

                        // Retrieve associated persona
                        chiavata.setPersona(getPersonaForChiavata(chiavata.getId()));

                        chiavate.add(chiavata);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error retrieving Chiavate", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return chiavate;
    }

    /**
     * Get tags for a specific Chiavata
     * @param chiavataId ID of the Chiavata
     * @return List of tag names
     */
    private ArrayList<String> getTagsForChiavata(long chiavataId) {
        ArrayList<String> tags = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(
                    "SELECT nome_tag FROM Tags " +
                            "JOIN Rapporto_Tags ON Tags.id_tag = Rapporto_Tags.id_tag " +
                            "WHERE Rapporto_Tags.id_rapporto = ?",
                    new String[]{String.valueOf(chiavataId)}
            );

            if (cursor != null) {
                int nomeTagIndex = cursor.getColumnIndex("nome_tag");
                if (nomeTagIndex != -1) {
                    while (cursor.moveToNext()) {
                        tags.add(cursor.getString(nomeTagIndex));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error retrieving tags", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return tags;
    }

    /**
     * Get Persona for a specific Chiavata
     * @param chiavataId ID of the Chiavata
     * @return Persona object
     */
    private Persona getPersonaForChiavata(long chiavataId) {
        Persona persona = null;
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(
                    "SELECT p.id_persona, p.nome, p.nickname, p.genere FROM Persone p " +
                            "JOIN Rapporti_Persone rp ON p.id_persona = rp.id_persona " +
                            "WHERE rp.id_rapporto = ?",
                    new String[]{String.valueOf(chiavataId)}
            );

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id_persona");
                int nomeIndex = cursor.getColumnIndex("nome");
                int nicknameIndex = cursor.getColumnIndex("nickname");
                int genereIndex = cursor.getColumnIndex("genere");

                if (idIndex != -1 && nomeIndex != -1 && nicknameIndex != -1 && genereIndex != -1) {
                    persona = new Persona(
                            cursor.getString(nomeIndex),
                            cursor.getString(genereIndex)
                    );
                    // If you need to set additional properties like ID
                    // persona.setId(cursor.getInt(idIndex));
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
}