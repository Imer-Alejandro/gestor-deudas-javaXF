package com.example.cashdebt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class AjustesController {
    @FXML
    private Stage primaryStage;
    @FXML
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    TextField nuevoUsuario,oldPass,newPass;

    public void actualizarDatos(){
        if (Objects.equals(nuevoUsuario.getText(), "") || Objects.equals(oldPass.getText(), "")
                || Objects.equals(newPass.getText(), "")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al actualizar");
            alert.setHeaderText(null);
            alert.setContentText("debe ingresar los datos!");
            alert.showAndWait();
        }else {
            Operaciones.actualizarUsuario(nuevoUsuario.getText(),newPass.getText(),oldPass.getText());

            nuevoUsuario.setText("");
            newPass.setText("");
            oldPass.setText("");
        }
    }


    public void cambioViewPanelControl(){
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
    }
}
