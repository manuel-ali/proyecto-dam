package com.example._proyecto;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {
    HelloController controller = new HelloController(this);
    ObservableList<Coche> cochesOL = FXCollections.observableArrayList();

    private TableView<Coche> tableViewUsuario;
    private TableView<Coche> tableViewAdmin;
//    private Button inicio;
    private Button registrarse;
    private Button inicioSesionUsuario;
    private Button inicioSesionAdministrador;
    private Button anadir;
    private HBox hboxUsuario;
    private HBox hboxAdministrador;
    private Scene sceneUsuario;
    private Scene sceneAdmin;
    private Scene sceneInicioSesion;
    private Persona usuarioActivo;

    @Override
    public void start(Stage primaryStage) {
        ConexionBd.conectar();
        controller.importar();
        controller.insertarUsuarioAdmin();

        cochesOL = FXCollections.observableArrayList(controller.getCoches());

        setSceneAdmin();
        setSceneUsuario();

        primaryStage.setTitle("Gran Turismo 4");
        primaryStage.setScene(sceneUsuario);
        primaryStage.show();

//        inicio.setOnAction(event -> {
//            cochesOL = FXCollections.observableArrayList(controller.getCoches());
//            if (!cochesOL.isEmpty()){
//                tableView.setItems(cochesOL);
//            }
//        });

        inicioSesionUsuario.setOnAction(event -> {
            setSceneInicioSesion(primaryStage);
            primaryStage.setScene(sceneInicioSesion);
            primaryStage.show();
        });

        inicioSesionAdministrador.setOnAction(actionEvent -> {
            setSceneInicioSesion(primaryStage);
            primaryStage.setScene(sceneInicioSesion);
            primaryStage.show();
        });

        anadir.setOnAction(actionEvent -> {
            anadir();
            cochesOL = FXCollections.observableArrayList(controller.getCoches());
            tableViewUsuario.setItems(cochesOL);
            tableViewAdmin.setItems(cochesOL);
        });
    }

    public void setSceneUsuario(){
        hboxUsuario = new HBox();

        inicioSesionUsuario = new Button("Inicio sesión");

        tableViewUsuario = crearTableView();
        tableViewUsuario.setItems(cochesOL);

        hboxUsuario.getChildren().addAll(inicioSesionUsuario);
        hboxUsuario.setSpacing(10);
        hboxUsuario.setPadding(new Insets(10, 0, 0, 10));

        GridPane gridPaneUsuario = new GridPane();
        gridPaneUsuario.add(hboxUsuario, 0, 0);
        gridPaneUsuario.add(tableViewUsuario, 0, 1);
        gridPaneUsuario.setVgap(10);

        sceneUsuario = new Scene(gridPaneUsuario, 800, 600);
    }

    public void setSceneAdmin(){
        hboxAdministrador = new HBox();

        inicioSesionAdministrador = new Button("Inicio sesión");
        anadir = new Button("Añadir");

        tableViewAdmin = crearTableView();
        tableViewAdmin.setItems(cochesOL);

        hboxAdministrador.getChildren().addAll(inicioSesionAdministrador,anadir);
        hboxAdministrador.setSpacing(10);
        hboxAdministrador.setPadding(new Insets(10, 0, 0, 10));

        GridPane gridPaneAdmin = new GridPane();
        gridPaneAdmin.add(hboxAdministrador, 0, 0);
        gridPaneAdmin.add(tableViewAdmin, 0, 1);
        gridPaneAdmin.setVgap(10);

        sceneAdmin = new Scene(gridPaneAdmin, 800, 600);
    }

    public void setSceneInicioSesion(Stage primaryStage){
        sceneInicioSesion = iniciarSesion(primaryStage);
    }

    public void anadir() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Anadir");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label marcaLabel = new Label("Marca:");
        GridPane.setConstraints(marcaLabel, 0, 0);
        TextField marcaInput = new TextField();
        GridPane.setConstraints(marcaInput, 1, 0);

        Label paisLabel = new Label("Pais:");
        GridPane.setConstraints(paisLabel, 0, 1);
        TextField paisInput = new TextField();
        GridPane.setConstraints(paisInput, 1, 1);

        Label nombreLabel = new Label("Nombre:");
        GridPane.setConstraints(nombreLabel, 0, 2);
        TextField nombreInput = new TextField();
        GridPane.setConstraints(nombreInput, 1, 2);

        Label anyoLabel = new Label("Año:");
        GridPane.setConstraints(anyoLabel, 0, 3);
        TextField anyoInput = new TextField();
        GridPane.setConstraints(anyoInput, 1, 3);

        Label mensajeExito = new Label("SE HA AÑADIDO UN NUEVO COCHE A LA BASE DE DATOS");
        mensajeExito.setVisible(false);
        GridPane.setConstraints(mensajeExito, 0, 4);

        Label mensajeNum = new Label("El AÑO HA DE CONTENER NUMEROS");
        mensajeNum.setVisible(false);
        GridPane.setConstraints(mensajeNum, 0, 4);

        Label mensajeCampos = new Label("NO PUEDES INTRODUCIR CARÁCTERES ESPECIALES");
        mensajeCampos.setVisible(false);
        GridPane.setConstraints(mensajeCampos, 0, 4);

        Label cocheExiste = new Label("YA EXISTE UN COCHE CON ESE NOMBRE");
        cocheExiste.setVisible(false);
        GridPane.setConstraints(cocheExiste, 0, 4);

        Button addButton = new Button("Añadir coche");
        GridPane.setConstraints(addButton, 1, 8);
        addButton.setOnAction(e -> {
            String regex ="[0-9]{0,4}";
            String regex2 ="[A-Za-z0-9 .-]+";

            String marca = marcaInput.getText();
            String pais = paisInput.getText();
            String nombre = nombreInput.getText();
            String anyo = anyoInput.getText();

            boolean exito = true;
            if (!anyo.matches(regex)){
                exito = false;
                mensajeNum.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(ev -> mensajeNum.setVisible(false));
                pause.play();
            }else {
                exito = true;
            }

            if (!marca.matches(regex2) || !pais.matches(regex2) || !nombre.matches(regex2)){
                exito = false;
                mensajeCampos.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(ev -> mensajeCampos.setVisible(false));
                pause.play();
            }

            if (exito){
                if (controller.anadirCoche(marca,pais,nombre, Integer.parseInt(anyo))){
                    mensajeExito.setVisible(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(ev -> stage.close());
                    pause.play();
                }else {
                    cocheExiste.setVisible(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(ev -> stage.close());
                    pause.play();
                }
            }
        });

        grid.getChildren().addAll(marcaLabel, marcaInput,paisLabel,paisInput, nombreLabel, nombreInput, anyoInput,anyoLabel,addButton, mensajeExito, mensajeNum, mensajeCampos, cocheExiste);

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

    public TableView crearTableView(){
        TableView tableView = new TableView();

        TableColumn<Coche,String> marca = new TableColumn("Marca");
        marca.setCellValueFactory(e -> new javafx.beans.property.SimpleStringProperty(e.getValue().getMarca().getnombre()));
        marca.setPrefWidth(150);

        TableColumn <Coche, String> pais = new TableColumn("Pais");
        pais.setCellValueFactory(e -> new javafx.beans.property.SimpleStringProperty(e.getValue().getMarca().getPais()));
        pais.setPrefWidth(120);

        TableColumn<Coche, String> coche = new TableColumn("Coche");
        coche.setCellValueFactory(e -> new javafx.beans.property.SimpleStringProperty(e.getValue().getnombre()));
        coche.setPrefWidth(300);

        TableColumn<Coche, Integer> anyo = new TableColumn("Año");
        anyo.setCellValueFactory(new PropertyValueFactory<>("anyo"));
        anyo.setPrefWidth(50);

        tableView.getColumns().addAll(marca, pais, coche, anyo);

        tableView.setPrefWidth(1700);
        tableView.setPrefHeight(900);

        return tableView;
    }

    public Scene registrarNuevoUsuario(Stage primaryStage){
        Label usernameLabel = new Label("Nombre de usuario:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();

        Label nombreLabel = new Label("Nombre:");
        TextField nombreField = new TextField();

        Label apellidoLabel = new Label("Apellidos:");
        TextField apellidoField = new TextField();

        Label mensajeNombre = new Label("No se permiten carácteres especiales en nombre y/o apellidos");
        mensajeNombre.setVisible(false);

        Label usuarioCreado = new Label("Usuario creado con éxito");
        usuarioCreado.setVisible(false);

        Label usuarioExistente = new Label("Ya existe un usuario con ese nombre");
        usuarioExistente.setVisible(false);

        Label camposVacios = new Label("Has de completar todos los campos");
        camposVacios.setVisible(false);

        Label errorUsuario = new Label("Ha habido un error inesperado al registrar el usuario");
        errorUsuario.setVisible(false);

        Button inicio = new Button("Inicio");
        inicio.setVisible(true);

        Button inicioSesion = new Button("Iniciar sesion");
        inicioSesion.setVisible(true);

        registrarse = new Button("Registrarse");

        HBox buttonBox = new HBox(2, inicioSesion, registrarse);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(25);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(inicio, 0,0);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(nombreLabel, 0,3);
        gridPane.add(nombreField, 1, 3);
        gridPane.add(apellidoLabel, 0,4);
        gridPane.add(apellidoField, 1, 4);
        gridPane.add(buttonBox, 1, 5);
        gridPane.add(usuarioCreado, 1, 6);
        gridPane.add(usuarioExistente, 1, 6);
        gridPane.add(camposVacios, 1, 6);
        gridPane.add(mensajeNombre, 1, 6);
        gridPane.add(errorUsuario, 1, 6);

        inicio.setOnAction(e -> {
            primaryStage.setScene(sceneUsuario);
            primaryStage.show();
        });

        inicioSesion.setOnAction(e -> {
            Scene scene2 = iniciarSesion(primaryStage);
            primaryStage.setScene(scene2);
            primaryStage.show();
        });

        registrarse.setOnAction(e -> {
            String regex = "[a-zA-Z-]+";
            String regexNombreUsuario = "[a-zA-Z0-9_-]+";

            boolean exitoCamposTexto = true;
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || nombreField.getText().isEmpty() || apellidoField.getText().isEmpty() || !usernameField.getText().matches(regexNombreUsuario) || !nombreField.getText().matches(regex) || !apellidoField.getText().matches(regex)) {
                exitoCamposTexto = false;
                camposVacios.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(ev -> camposVacios.setVisible(false));
                pause.play();
            }else {
                if (!usernameField.getText().matches(regexNombreUsuario) || !nombreField.getText().matches(regex) || !apellidoField.getText().matches(regex)) {
                    exitoCamposTexto = false;
                    mensajeNombre.setVisible(true);
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
                    pauseTransition.setOnFinished(ev -> mensajeNombre.setVisible(false));
                    pauseTransition.play();
                }
            }

            if (exitoCamposTexto) {
                if (controller.usuarioExiste(usernameField.getText())){
                    usuarioExistente.setVisible(true);
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                    pauseTransition.setOnFinished(ev -> usuarioExistente.setVisible(false));
                    pauseTransition.play();
                }else {
                    Usuario usuario = new Usuario(nombreField.getText(),apellidoField.getText(),usernameField.getText(),passwordField.getText());
                    if (controller.registrarUsuario(usuario)){
                        usuarioCreado.setVisible(true);
                        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                        pauseTransition.setOnFinished(ev -> {
                            usuarioCreado.setVisible(true);
                            primaryStage.setScene(sceneInicioSesion);
                            primaryStage.show();
                        });
                        pauseTransition.play();
                    }else {
                        errorUsuario.setVisible(true);
                        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
                        pauseTransition.setOnFinished(ev -> errorUsuario.setVisible(false));
                        pauseTransition.play();
                    }

                }
            }
        });

        Scene scene1 = new Scene(gridPane, 800, 600);
        return scene1;
    }

    public Scene iniciarSesion(Stage primaryStage){
        Label usernameLabel = new Label("Nombre de usuario:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();

        Label mensajeNombre = new Label("El nombre de usuario no puede contener carácteres especiales.");
        mensajeNombre.setVisible(false);

        Label usuarioInexistente = new Label("No existe ningún usuario con ese nombre");
        usuarioInexistente.setVisible(false);

        Label camposVacios = new Label("Has de introducir un nombre y una contraseña");
        camposVacios.setVisible(false);

        Label inicioSesionCorrecto = new Label("Se ha iniciado sesión correctamente");
        inicioSesionCorrecto.setVisible(false);

        Button iniciarSesion = new Button("Iniciar sesión");
        iniciarSesion.setVisible(true);

        Button inicio = new Button("Inicio");
        inicio.setVisible(true);

        registrarse = new Button("Registrarse");
        registrarse.setVisible(true);

        HBox buttonBox = new HBox(2, iniciarSesion, registrarse);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(25);

        GridPane gridPane = new GridPane();
        GridPane.setColumnSpan(usernameField,2);
        GridPane.setColumnSpan(passwordField,2);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(inicio, 0,0);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1, 2,1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2,2,1);
        gridPane.add(buttonBox, 1, 3);
        gridPane.add(usuarioInexistente, 1, 4);
        gridPane.add(camposVacios, 1, 4);
        gridPane.add(mensajeNombre, 1, 4);
        gridPane.add(inicioSesionCorrecto, 1, 4);

        inicio.setOnAction(e -> {
            primaryStage.setScene(sceneUsuario);
            primaryStage.show();
            usernameField.clear();
            passwordField.clear();
        });

        registrarse.setOnAction(e -> {
            Scene scene3 = registrarNuevoUsuario(primaryStage);
            primaryStage.setScene(scene3);
            primaryStage.show();
            usernameField.clear();
            passwordField.clear();
        });

        iniciarSesion.setOnAction(e -> {
            String regex = "[a-zA-Z0-9_-]+";
            boolean exito;
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                exito = false;
                camposVacios.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(ev -> camposVacios.setVisible(false));
                pause.play();
            }else {
                if (!usernameField.getText().matches(regex)) {
                    exito = false;
                    mensajeNombre.setVisible(true);
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                    pauseTransition.setOnFinished(ev -> mensajeNombre.setVisible(false));
                    pauseTransition.play();
                }else {
                    exito = true;
                }
            }

            if (exito) {
                if (!controller.usuarioExiste(usernameField.getText())) {
                    usuarioInexistente.setVisible(true);
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                    pauseTransition.setOnFinished(ev -> usuarioInexistente.setVisible(false));
                    pauseTransition.play();
                } else {
                    inicioSesionCorrecto.setVisible(true);
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));

                    usuarioActivo = controller.obtenerTipoUsuario(usernameField.getText());
                    usuarioActivo.setId(controller.obtenerIdUsuario(usernameField.getText()));

                    if (usuarioActivo instanceof Administrador){
                        pauseTransition.setOnFinished(ev -> {
                            inicioSesionCorrecto.setVisible(false);
                            primaryStage.setScene(sceneAdmin);
                            primaryStage.show();
                        });
                        pauseTransition.play();
                    }else {
                        pauseTransition.setOnFinished(ev -> {
                            inicioSesionCorrecto.setVisible(false);
                            primaryStage.setScene(sceneUsuario);
                            primaryStage.show();
                        });
                        pauseTransition.play();
                    }
                }
            }
        });

        Scene scene2 = new Scene(gridPane, 800, 600);
        return scene2;
    }

    public static void main(String[] args) {
        launch();
    }
}