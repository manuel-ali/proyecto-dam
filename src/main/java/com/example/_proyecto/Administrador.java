package com.example._proyecto;

public class Administrador extends Persona{
    private final char TIPO = 'a';

    public char getTIPO() {
        return TIPO;
    }

    public Administrador(String nombre, String apellido,String nombreUsuario, String password) {
        super(nombre, apellido, nombreUsuario, password);
    }

    public Administrador() {
    }
}
