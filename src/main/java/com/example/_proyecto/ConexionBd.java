package com.example._proyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBd {
    private static Connection connection = null;

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        ConexionBd.connection = connection;
    }

    public static void conectarRoot(){
        String rootUser = "root";
        String rootPass = "root";
        String rootUrl = "jdbc:mysql://localhost:3306/";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(rootUrl, rootUser, rootPass);

            if (connection == null) {
                System.out.println("Fallo al conectar");
            }

        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void conectarGt4(){
        String gt4User = "gt4";
        String gt4Pass = "spooneg";
        String gt4Url = "jdbc:mysql://localhost:3306/granturismo4";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(gt4Url, gt4User, gt4Pass);

            if (connection == null) {
                System.out.println("Fallo al conectar con el usuario gt4.");
            }

        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void crearUsuario(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conectarRoot();

            Statement stmnt = connection.createStatement();

            stmnt.execute("create database if not exists granturismo4\n" +
                    "default character set utf8 collate utf8_general_ci;");
            stmnt.execute("use granturismo4;");

            if (!InsercionBd.usuarioGt4Existe("gt4")){
                stmnt.execute("create user 'gt4'@'localhost' identified by 'spooneg';");
                stmnt.execute("grant insert,select, create, alter, update, delete on granturismo4.* to'gt4'@'localhost';\n");
            }

            stmnt.close();

            conectarGt4();
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public static void crearTablaMarcas(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Statement stmnt = connection.createStatement();

            stmnt.execute("create table if not exists marcas(\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    nombre varchar(150) not null,\n" +
                    "    pais varchar(150) not null\n" +
                    ");\n");

            stmnt.close();
        }catch (SQLException | ClassNotFoundException e ){
            System.out.println(e.getMessage());
        }
    }

    public static void crearTablacoches(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Statement stmnt = connection.createStatement();

            stmnt.execute("create table if not exists coches(\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    marca integer not null,\n" +
                    "\tnombre varchar(150),\n" +
                    "    anyo integer not null,\n" +
                    "constraint fk1_coches_marca foreign key (marca) references marcas(id) on update cascade on delete no action\n" +
                    ");");

            stmnt.close();
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public static void crearTablaUsuarios(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Statement statement = connection.createStatement();

            statement.execute("create table if not exists usuarios(\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    nombre varchar(30) not null,\n" +
                    "    apellidos varchar(60) not null,\n" +
                    "    nombreUsuario varchar(60) unique,\n" +
                    "    pass varchar(30) not null,\n" +
                    "    tipo char(1) default 'u' \n" +
                    ");");

            statement.close();
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public static void crearTablaGaraje(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Statement statement = connection.createStatement();

            statement.execute("create table if not exists garaje(\n" +
                    "\tusuario int,\n" +
                    "    coche int,\n" +
                    "\tprimary key (usuario, coche),\n" +
                    "constraint fk2_garaje_usuarios foreign key (usuario) references usuarios(id) on update cascade on delete no action,\n" +
                    "constraint fk3_garaje_coche foreign key (coche) references coches(id) on update cascade on delete no action\n" +
                    ");");
            statement.close();
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public static void desconectar(){
        try {
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void conectar(){
        crearUsuario();
        crearTablaMarcas();
        crearTablacoches();
        crearTablaUsuarios();
        crearTablaGaraje();
    }
}
