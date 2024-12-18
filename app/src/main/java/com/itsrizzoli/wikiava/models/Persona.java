package com.itsrizzoli.wikiava.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Objects;

public class Persona {
    private int id;
    private String nome;
    private String genere;

    public Persona(String nome) {
        this.nome = nome;
    }

    public Persona(String nome, String genere) {
        this.nome = nome;
        this.genere = genere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == persona.id && Objects.equals(nome, persona.nome) && Objects.equals(genere, persona.genere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, genere);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", genere='" + genere + '\'' +
                '}';
    }
}
