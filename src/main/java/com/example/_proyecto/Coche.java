package com.example._proyecto;

public class Coche {
    private int id;
    private Marca marca;
    private String nombre;
    private int anyo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public Coche(int id, Marca marca, String nombre, int anyo) {
        this.id = id;
        this.marca = marca;
        this.nombre = nombre;
        this.anyo = anyo;
    }

    public Coche(Marca marca, String nombre, int anyo) {
        this.marca = marca;
        this.nombre = nombre;
        this.anyo = anyo;
    }

    public Coche() {
    }

    @Override
    public String toString() {
        return "Coche{" +
                "marca=" + marca +
                ", nombre='" + nombre + '\'' +
                ", anyo=" + anyo +
                '}';
    }
}
