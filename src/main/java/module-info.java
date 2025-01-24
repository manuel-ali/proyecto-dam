module com.example._proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports com.example._proyecto;
    opens com.example._proyecto to javafx.fxml;
}