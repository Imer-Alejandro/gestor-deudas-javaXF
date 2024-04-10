package com.example.cashdebt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.TextField;
public class HelloController {
    @FXML
    private Stage primaryStage;
    @FXML
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    TextField usuario,clave;

    @FXML
    public void iniciarSession() {
        // Obtener el usuario y la contraseña ingresados
        String nombreUsuario = usuario.getText();
        String password = clave.getText();

        // Establecer la conexión a la base de datos SQLite
        Connection con = conetSQL.connect();

        if (con != null) {
            try {
                // Crear una sentencia para ejecutar consultas SQL
                Statement statement = con.createStatement();

                // Consultar si existe un usuario con la contraseña proporcionada
                String query = "SELECT COUNT(*) AS count FROM ajustes WHERE usuario = '" + nombreUsuario + "' AND clave = '" + password + "'";
                ResultSet resultSet = statement.executeQuery(query);

                // Obtener el resultado de la consulta
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    if (count > 0) {
                        // El usuario y la contraseña son válidos
                        try {
                            // Cargar la vista 2
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
                            Parent root = loader.load();

                            // Obtener el controlador de la vista 2
                            InicioController controlador = loader.getController();
                            controlador.setPrimaryStage(primaryStage);

                            // Configurar la escena y mostrarla
                            Scene scene = new Scene(root,1300, 680);
                            primaryStage.setScene(scene);
                            primaryStage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // El usuario o la contraseña son incorrectos
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error de inicio de sesión");
                        alert.setHeaderText(null);
                        alert.setContentText("El usuario o la contraseña son incorrectos");
                        alert.showAndWait();
                    }
                }

                // Cerrar la conexión y la declaración
                resultSet.close();
                statement.close();
                con.close();
            } catch (SQLException e) {
                System.out.println("Error en la consulta: " + e.getMessage());
            }
        }
    }
}

