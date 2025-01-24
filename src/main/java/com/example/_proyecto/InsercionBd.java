package com.example._proyecto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsercionBd {
    private static final String INSERTARMARCA = "insert into marcas (nombre, pais) values (?,?)";
    private static final String INSERTARCOCHE = "insert into coches (marca,nombre,anyo) values (?,?,?)";
    private static final String INSERTARUSUARIO = "INSERT INTO usuarios (nombre, apellidos, nombreUsuario, pass) VALUES (?, ?, ?, ?)";
    private static final String INSERTARADMINISTRADOR = "INSERT INTO usuarios (nombre,apellidos, nombreUsuario, pass, tipo) VALUES (?, ?, ?, ?, ?)";

    public static boolean usuarioGt4Existe(String nombre) {
        String query = "SELECT 1 FROM mysql.user WHERE user = 'gt4';";
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void insertarMarca(String nombre, String pais) {
        try(PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(INSERTARMARCA)){
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, pais);

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertarCoche(int marca, String nombre, int anyo) {
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(INSERTARCOCHE)){
            preparedStatement.setInt(1,marca);
            preparedStatement.setString(2, nombre);
            preparedStatement.setInt(3, anyo);

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int obtenerIdMarca(String nombre) {
        String query = "select id from marcas where nombre = ?";
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)){
            preparedStatement.setString(1, nombre);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                int id = resultSet.getInt(1);
                if (resultSet != null){
                    return id;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return -1;
    }

    public static int obtenerIdCoche(String nombre, int anyo) {
        String query = "select id from coches where nombre = ? and anyo = ?";
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)){
            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, anyo);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                int id = resultSet.getInt(1);
                if (resultSet != null){
                    return id;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return -1;
    }

    public static boolean marcaExiste(String marca) {
        String query = "SELECT 1 from marcas where nombre = ?";
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)){
            preparedStatement.setString(1, marca);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean cocheExiste(int marca, String nombre, int anyo) {
        String query = "SELECT 1 from coches where marca = ? and nombre = ? and anyo = ?";
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, marca);
            preparedStatement.setString(2, nombre);
            preparedStatement.setInt(3, anyo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean contieneMarcas(){
        String query = "SELECT count(*) FROM marcas";
        try(PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean contieneCoches(){
        String query = "SELECT count(*) FROM coches";
        try(PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static List<Coche> obtenerDatos(){
        List<Coche> coches = new ArrayList<>();
        String queryCoche = "SELECT coches.id, coches.marca, coches.nombre, coches.anyo, marcas.nombre, marcas.pais FROM coches, marcas where coches.marca = marcas.id";

        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(queryCoche)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    Marca marca = new Marca(resultSet.getInt(2), resultSet.getString(5), resultSet.getString(6));
                    coches.add(new Coche(resultSet.getInt(1),marca,resultSet.getString(3),resultSet.getInt(4)));
                }
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return coches;
    }

    public static boolean adminExiste(String nombre, String apellidos,String nombreUsuario, char tipo) {
        String query = "SELECT 1 FROM usuarios where nombre = ? and apellidos = ? and nombreUsuario = ? and tipo = ?";
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellidos);
            preparedStatement.setString(3, nombreUsuario);
            preparedStatement.setString(4, String.valueOf(tipo));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean usuarioExiste(String nombreUsuario) {
        String query = "SELECT 1 from usuarios where nombreUsuario = ?";
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, nombreUsuario);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void insertarUsuario(String nombre,String apellidos, String nombreUsuario, String password) {
        try(PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(INSERTARUSUARIO)){
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellidos);
            preparedStatement.setString(3, nombreUsuario);
            preparedStatement.setString(4, password);

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertarAdmin(String nombre, String apellidos, String nombreUsuario, String password, char tipo) {
        try(PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(INSERTARADMINISTRADOR)){
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellidos);
            preparedStatement.setString(3, nombreUsuario);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, String.valueOf(tipo));

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static char obtenerTipoUsuario(String nombreUsuario) {
        String query = "SELECT tipo FROM usuarios where nombreUsuario = ?";
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)){
            preparedStatement.setString(1, nombreUsuario);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    return resultSet.getString(1).charAt(0);
                }
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static int obtenerIdUsuario(String nombreUsuario) {
        String query = "SELECT id FROM usuarios WHERE nombreUsuario = ?";
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, nombreUsuario);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                int id = resultSet.getInt(1);
                if (resultSet != null){
                    return id;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static boolean obtenerGaraje(int idUsuario, int idCoche) {
        String query = "SELECT * FROM garaje WHERE usuario = ? AND coche = ?";
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)){
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setInt(2, idCoche);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void insertarGaraje(int idUsuario, int idCoche) {
        String query = "INSERT INTO garaje (usuario,coche) VALUES(?,?)";
        try (PreparedStatement preparedStatement = ConexionBd.getConnection().prepareStatement(query)){
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setInt(2, idCoche);

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
