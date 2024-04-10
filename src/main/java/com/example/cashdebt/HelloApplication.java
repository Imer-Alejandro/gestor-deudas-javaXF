package com.example.cashdebt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        // Obtener la instancia del controlador después de cargar la vista
        HelloController controller = fxmlLoader.getController();
        // Establecer la instancia de Stage en el controlador
        controller.setPrimaryStage(stage);

        Scene scene = new Scene(root, 1300, 680);
        stage.setTitle("casHDebet");
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("img/logo.png"))));
        stage.show();

        // Establecer la conexión a la base de datos SQLite
    }


    public static void main(String[] args) {
        launch();
    }
}