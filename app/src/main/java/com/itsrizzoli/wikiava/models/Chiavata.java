package com.itsrizzoli.wikiava.models;

import java.util.ArrayList;
import java.util.Objects;

public class Chiavata {
    private int id;
    private Persona persona;
    private float voto;
    private String luogo;
    private String posto;
    private String data;
    private String descrizione;
    private ArrayList<String> tags;
    private int numO;
    private int minuti;
    private int numRound;
    private boolean usatoProtezioni;

    public Chiavata() {
    }

    public Chiavata(Persona persona) {
        this.persona = persona;
    }

    public Chiavata(Persona persona, float voto) {
        this.persona = persona;
        this.voto = voto;
    }

    public Chiavata(Persona persona, float voto, String luogo, String posto, String data, String descrizione, ArrayList<String> tags, boolean usatoProtezioni) {
        this.persona = persona;
        this.voto = voto;
        this.luogo = luogo;
        this.posto = posto;
        this.data = data;
        this.descrizione = descrizione;
        this.tags = tags;
        this.usatoProtezioni = usatoProtezioni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public float getVoto() {
        return voto;
    }

    public void setVoto(float voto) {
        this.voto = voto;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getNumO() {
        return numO;
    }

    public void setNumO(int numO) {
        this.numO = numO;
    }

    public int getMinuti() {
        return minuti;
    }

    public void setMinuti(int minuti) {
        this.minuti = minuti;
    }

    public int getNumRound() {
        return numRound;
    }

    public void setNumRound(int numRound) {
        this.numRound = numRound;
    }

    public boolean isUsatoProtezioni() {
        return usatoProtezioni;
    }

    public void setUsatoProtezioni(boolean usatoProtezioni) {
        this.usatoProtezioni = usatoProtezioni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chiavata chiavata = (Chiavata) o;
        return id == chiavata.id && Float.compare(voto, chiavata.voto) == 0 && numO == chiavata.numO && minuti == chiavata.minuti && numRound == chiavata.numRound && usatoProtezioni == chiavata.usatoProtezioni && Objects.equals(persona, chiavata.persona) && Objects.equals(luogo, chiavata.luogo) && Objects.equals(posto, chiavata.posto) && Objects.equals(data, chiavata.data) && Objects.equals(descrizione, chiavata.descrizione) && Objects.equals(tags, chiavata.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, persona, voto, luogo, posto, data, descrizione, tags, numO, minuti, numRound, usatoProtezioni);
    }

    @Override
    public String toString() {
        return "Chiavata{" +
                "id=" + id +
                ", persona=" + persona +
                ", voto=" + voto +
                ", luogo='" + luogo + '\'' +
                ", posto='" + posto + '\'' +
                ", data='" + data + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", tags=" + tags +
                ", numO=" + numO +
                ", minuti=" + minuti +
                ", numRound=" + numRound +
                ", usatoProtezioni=" + usatoProtezioni +
                '}';
    }
}
