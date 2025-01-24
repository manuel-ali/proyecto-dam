package com.example._proyecto;

public class Marca {
    private int id;
    private String nombre;
    private String pais;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Marca(int id, String nombre, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
    }

    public Marca(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public Marca() {
    }

    @Override
    public String toString() {
        return "Marca{" +
                "nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
