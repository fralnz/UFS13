package com.itsrizzoli.wikiava.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Information
    private static final String DATABASE_NAME = "storage.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_PERSONE = "Persone";
    private static final String TABLE_RAPPORTI = "Rapporti";
    private static final String TABLE_RAPPORTI_PERSONE = "Rapporti_Persone";
    private static final String TABLE_TAGS = "Tags";
    private static final String TABLE_RAPPORTO_TAGS = "Rapporto_Tags";

    // Persone Table Columns
    private static final String COLUMN_ID_PERSONA = "id_persona";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_NICKNAME = "nickname";
    private static final String COLUMN_GENERE = "genere";

    // Rapporti Table Columns
    private static final String COLUMN_ID_RAPPORTO = "id_rapporto";
    private static final String COLUMN_DATA_RAPPORTO = "data_rapporto";
    private static final String COLUMN_TITOLO_EVENTO = "titolo_evento";
    private static final String COLUMN_VOTO = "voto";
    private static final String COLUMN_USATO_PROTEZIONE = "usato_protezione";
    private static final String COLUMN_DESCRIZIONE = "descrizione";

    // Tags Table Columns
    private static final String COLUMN_ID_TAG = "id_tag";
    private static final String COLUMN_NOME_TAG = "nome_tag";

    // Create Table Statements
    private static final String CREATE_TABLE_PERSONE = "CREATE TABLE " + TABLE_PERSONE + "("
            + COLUMN_ID_PERSONA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NOME + " VARCHAR(255), "
            + COLUMN_NICKNAME + " VARCHAR(255), "
            + COLUMN_GENERE + " VARCHAR(50)"
            + ")";

    private static final String CREATE_TABLE_RAPPORTI = "CREATE TABLE " + TABLE_RAPPORTI + "("
            + COLUMN_ID_RAPPORTO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATA_RAPPORTO + " DATE, "
            + COLUMN_TITOLO_EVENTO + " VARCHAR(255), "
            + COLUMN_VOTO + " INTEGER, "
            + COLUMN_USATO_PROTEZIONE + " BOOLEAN, "
            + COLUMN_DESCRIZIONE + " TEXT"
            + ")";

    private static final String CREATE_TABLE_RAPPORTI_PERSONE = "CREATE TABLE " + TABLE_RAPPORTI_PERSONE + "("
            + COLUMN_ID_RAPPORTO + " INTEGER, "
            + COLUMN_ID_PERSONA + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_ID_RAPPORTO + ") REFERENCES " + TABLE_RAPPORTI + "(" + COLUMN_ID_RAPPORTO + "), "
            + "FOREIGN KEY(" + COLUMN_ID_PERSONA + ") REFERENCES " + TABLE_PERSONE + "(" + COLUMN_ID_PERSONA + ")"
            + ")";

    private static final String CREATE_TABLE_TAGS = "CREATE TABLE " + TABLE_TAGS + "("
            + COLUMN_ID_TAG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NOME_TAG + " VARCHAR(255)"
            + ")";

    private static final String CREATE_TABLE_RAPPORTO_TAGS = "CREATE TABLE " + TABLE_RAPPORTO_TAGS + "("
            + COLUMN_ID_RAPPORTO + " INTEGER, "
            + COLUMN_ID_TAG + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_ID_RAPPORTO + ") REFERENCES " + TABLE_RAPPORTI + "(" + COLUMN_ID_RAPPORTO + "), "
            + "FOREIGN KEY(" + COLUMN_ID_TAG + ") REFERENCES " + TABLE_TAGS + "(" + COLUMN_ID_TAG + ")"
            + ")";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Create all tables
        database.execSQL(CREATE_TABLE_PERSONE);
        database.execSQL(CREATE_TABLE_RAPPORTI);
        database.execSQL(CREATE_TABLE_TAGS);
        database.execSQL(CREATE_TABLE_RAPPORTI_PERSONE);
        database.execSQL(CREATE_TABLE_RAPPORTO_TAGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // Drop existing tables
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RAPPORTO_TAGS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RAPPORTI_PERSONE);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TAGS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RAPPORTI);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONE);

        // Recreate tables
        onCreate(database);
    }
}