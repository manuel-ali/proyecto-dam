package com.example._proyecto;

import java.util.ArrayList;

public class Usuario extends Persona{
    ArrayList<Coche> garaje = new ArrayList<>();

    public ArrayList<Coche> getGaraje() {
        return garaje;
    }

    public void setGaraje(ArrayList<Coche> garaje) {
        this.garaje = garaje;
    }

    public Usuario(String nombre, String apellido,String nombreUsuario, String password) {
        super(nombre, apellido,nombreUsuario, password);
    }

    public Usuario() {
    }

    @Override
    public String toString() {
        return "Usuario{" +
                super.toString() +
                "garaje=" + garaje +
                '}';
    }
}
