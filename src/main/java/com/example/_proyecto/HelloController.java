package com.example._proyecto;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    private HelloApplication vista;

    List<Coche> coches = new ArrayList<>();

    public List<Coche> getCoches() {
        return coches;
    }

    public void setCoches(List<Coche> coches) {
        this.coches = coches;
    }

    public HelloController(HelloApplication vista) {
        this.vista = vista;
    }

    public HelloController(){}

    public void importar(){
        Importar importar = new Importar();

        coches = importar.importar();
    }

    public void insertarUsuarioAdmin(){
        Administrador admin = new Administrador("manuel","ali","manuelali","vivagt4");
        if (!InsercionBd.adminExiste(admin.getNombre(), admin.getApellido(), admin.getNombreUsuario(), admin.getTIPO())){
            InsercionBd.insertarAdmin(admin.getNombre(), admin.getApellido(), admin.getNombreUsuario(), admin.getPassword(),admin.getTIPO());
        }
    }

    public boolean registrarUsuario(Usuario usuario){
        if (!InsercionBd.usuarioExiste(usuario.getNombreUsuario())){
            InsercionBd.insertarUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getNombreUsuario(), usuario.getPassword());
            return true;
        }
        return false;
    }

    public Persona obtenerTipoUsuario(String nombreUsuario){
        Persona persona;
        if (InsercionBd.obtenerTipoUsuario(nombreUsuario) == 'a'){
            persona = new Administrador();
        }else {
            persona = new Usuario();
        }
        return persona;
    }

    public boolean usuarioExiste(String nombreUsuario){
        return InsercionBd.usuarioExiste(nombreUsuario);
    }

    public int obtenerIdUsuario(String nombreUsuario){
        return InsercionBd.obtenerIdUsuario(nombreUsuario);
    }

    public boolean anadirCoche(String marca, String pais, String nombre, int anyo){
        Importar importar = new Importar();
        if (InsercionBd.marcaExiste(marca)){
            int idMarca = InsercionBd.obtenerIdMarca(marca);
            if (!InsercionBd.cocheExiste(idMarca, nombre, anyo)){
                InsercionBd.insertarCoche(idMarca, nombre, anyo);
                coches = importar.obtenerDatos();
                return true;
            }
        }else {
            InsercionBd.insertarMarca(nombre,pais);
            int idMarca = InsercionBd.obtenerIdMarca(marca);
            if (!InsercionBd.cocheExiste(idMarca, nombre, anyo)){
                InsercionBd.insertarCoche(idMarca, nombre, anyo);
                coches = importar.obtenerDatos();
                return true;
            }
        }
        coches = importar.obtenerDatos();
        return false;
    }
}