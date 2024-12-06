package com.itsrizzoli.wikiava.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public final class DataList {
    private static DataList instance;
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<Persona> persone = new ArrayList<>();
    private ArrayList<Chiavata> chiavate = new ArrayList<>();
    private ArrayList<String> bondage = new ArrayList<>();
    private ArrayList<String> giochi = new ArrayList<>();



    private DataList() {
        tags.add("sporcona");
        tags.add("candida");
        tags.add("cucciolona");

        bondage.add("bavaglio");
        bondage.add("corda");
        bondage.add("manette");
        bondage.add("frustino");

        giochi.add("dildo");
        giochi.add("vibratore");
        giochi.add("strap-on");

        Persona pippo = new Persona("Pippo", "uomo");
        Persona ciccia = new Persona("Ciccia", "donna");
        Persona dragone = new Persona("Dragone", "leggendario");
        Persona lucia = new Persona("Lucia", "donna");
        persone.add(pippo);
        persone.add(ciccia);
        persone.add(dragone);
        persone.add(lucia);

        chiavate.add(new Chiavata(pippo, 5, "Shuttle", "NASA", "18/11/2023", "spaziale", tags, 2, 34, 2));
        chiavate.add(new Chiavata(ciccia, 3, "Shuttle", "NASA", "18/11/2023", "spaziale", tags, 2, 34, 2));
        chiavate.add(new Chiavata(dragone, 10, "Shuttle", "NASA", "18/11/2023", "spaziale", tags, 2, 34, 2));
        chiavate.add(new Chiavata(dragone, 10, "Shuttle", "NASA", "18/11/2023", "spaziale", tags, 2, 34, 2));
        chiavate.add(new Chiavata(dragone, 10, "Shuttle", "NASA", "18/11/2023", "spaziale", tags, 2, 34, 2));
        chiavate.add(new Chiavata(lucia, 7, "Shuttle", "NASA", "18/11/2023", "spaziale", tags, 2, 34, 2));
        chiavate.add(new Chiavata(lucia, 6, "Shuttle", "NASA", "18/11/2023", "spaziale", tags, 2, 34, 2));
    }

    public static DataList getInstance() {
        if (instance == null) {
            instance = new DataList();
        }
        return instance;
    }

    public static void setInstance(DataList instance) {
        DataList.instance = instance;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getBondage() {
        return bondage;
    }

    public void setBondage(ArrayList<String> bondage) {
        this.bondage = bondage;
    }
    public ArrayList<String> getGiochi() {
        return giochi;
    }

    public void setGiochi(ArrayList<String> giochi) {
        this.giochi = giochi;
    }

    public ArrayList<Persona> getPersone() {
        return persone;
    }

    public void setPersone(ArrayList<Persona> persone) {
        this.persone = persone;
    }

    public ArrayList<Chiavata> getChiavate() {
        return chiavate;
    }

    public void setChiavate(ArrayList<Chiavata> chiavate) {
        this.chiavate = chiavate;
    }

    public void addChiavata(Chiavata chiavata){
        this.chiavate.add(chiavata);
    }

    @NonNull
    @Override
    public String toString() {
        return "DataList{" +
                "tags=" + tags +
                ", persone=" + persone +
                ", chiavate=" + chiavate +
                ", bondage=" + bondage +
                ", giochi=" + giochi +
                '}';
    }
}
