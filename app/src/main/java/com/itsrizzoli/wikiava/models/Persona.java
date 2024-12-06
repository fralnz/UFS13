package com.itsrizzoli.wikiava.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Objects;

public class Persona {
    private String nome;
    private String genere;

    public int getBodyCount() {
        ArrayList<Chiavata> chiavate = DataList.getInstance().getChiavate();
        int bodyCount = 0;
        for (Chiavata chiavata : chiavate) {
            if (chiavata.getPersona().equals(this)) {
                bodyCount++;
            }
        }
        return bodyCount;
    }

    public Persona(String nome) {
        this.nome = nome;
    }

    public Persona(String nome, String genere) {
        this.nome = nome;
        this.genere = genere;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(nome, persona.nome) && Objects.equals(genere, persona.genere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, genere);
    }

    @NonNull
    @Override
    public String toString() {
        return nome + ' ' +
                "Scopate = " + getBodyCount();
    }
}
