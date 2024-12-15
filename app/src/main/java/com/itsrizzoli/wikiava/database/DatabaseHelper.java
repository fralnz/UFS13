package com.itsrizzoli.wikiava.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table: Persone
    private static final String TABLE_PERSONE = "Persone";
    private static final String COLUMN_PERSONE_ID = "ID_Persona";
    private static final String COLUMN_PERSONE_NOME = "nome";
    private static final String COLUMN_PERSONE_GENERE = "genere";

    private static final String CREATE_TABLE_PERSONE = "CREATE TABLE " + TABLE_PERSONE + " ("
            + COLUMN_PERSONE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PERSONE_NOME + " VARCHAR(100), "
            + COLUMN_PERSONE_GENERE + " VARCHAR(30)"
            + ");";

    // Table: Rapporti
    private static final String TABLE_RAPPORTI = "Rapporti";
    private static final String COLUMN_RAPPORTI_ID = "ID_Rapporti";
    private static final String COLUMN_RAPPORTI_NOME = "nome";
    private static final String COLUMN_RAPPORTI_LUOGO = "luogo";
    private static final String COLUMN_RAPPORTI_POSTO = "posto";
    private static final String COLUMN_RAPPORTI_DESCRIZIONE = "descrizione";
    private static final String COLUMN_RAPPORTI_BONDAGE = "bondage";
    private static final String COLUMN_RAPPORTI_GIOCHI = "giochi";
    private static final String COLUMN_RAPPORTI_DATA = "data_rapporto";
    private static final String COLUMN_RAPPORTI_VOTAZIONE = "votazione";
    private static final String COLUMN_RAPPORTI_PERSONA_ID = "ID_Persona";
    private static final String COLUMN_RAPPORTI_TAGS_ID = "ID_Tags";

    private static final String CREATE_TABLE_RAPPORTI = "CREATE TABLE " + TABLE_RAPPORTI + " ("
            + COLUMN_RAPPORTI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RAPPORTI_NOME + " VARCHAR(100), "
            + COLUMN_RAPPORTI_LUOGO + " VARCHAR(100), "
            + COLUMN_RAPPORTI_POSTO + " VARCHAR(100), "
            + COLUMN_RAPPORTI_DESCRIZIONE + " TEXT, "
            + COLUMN_RAPPORTI_BONDAGE + " VARCHAR(100), "
            + COLUMN_RAPPORTI_GIOCHI + " VARCHAR(100), "
            + COLUMN_RAPPORTI_DATA + " DATE, "
            + COLUMN_RAPPORTI_VOTAZIONE + " INTEGER, "
            + COLUMN_RAPPORTI_PERSONA_ID + " INTEGER, "
            + COLUMN_RAPPORTI_TAGS_ID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_RAPPORTI_PERSONA_ID + ") REFERENCES " + TABLE_PERSONE + "(" + COLUMN_PERSONE_ID + "), "
            + "FOREIGN KEY(" + COLUMN_RAPPORTI_TAGS_ID + ") REFERENCES Tags(ID_Tags)"
            + ");";

    // Table: Tags
    private static final String TABLE_TAGS = "Tags";
    private static final String COLUMN_TAGS_ID = "ID_Tags";
    private static final String COLUMN_TAGS_NOME = "nome";

    private static final String CREATE_TABLE_TAGS = "CREATE TABLE " + TABLE_TAGS + " ("
            + COLUMN_TAGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TAGS_NOME + " VARCHAR(100)"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PERSONE);
        db.execSQL(CREATE_TABLE_RAPPORTI);
        db.execSQL(CREATE_TABLE_TAGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAPPORTI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAGS);
        onCreate(db);
    }
}
