package com.example.cashdebt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientesController {
    @FXML
    private Stage primaryStage;
    @FXML
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    Label counterClientes,counterIngresos,counterDeuda;


    @FXML
    Pane viewCrearUser,viewAbono,viewDeuda,viewEditar,viewBusquedad;

    //datos de entrada para el registro de cliente
    @FXML
    TextField nombreNewCliente,telefonoNewCliente,cedulaNewCliente,comentarioNewCliente;

    //datos para editar el cliente
    @FXML
    TextField nombreEditCliente,telefonoEditCliente,cedulaEditCliente,comentarioEditCliente;
    //datos para el registro de abonos
    @FXML
    TextField montoAbono;
    @FXML
    ChoiceBox<String> listadoClienteExistentesAbono;


    //datos para el registro de deuda
    @FXML
    TextField montoDeuda;
    @FXML
    ChoiceBox<String> listadoClienteExistentes;

    //datos de la tabla


    //datos de la vista de resultados de busquedad y detalles del cliente
    @FXML
    Label resultNombre,resultComentario,resultTelefono,resultDataRegistro,resultCedula,resultDeudaGeneral,resultMontoAbonado,resultDeudaPendiente;

    @FXML
    ArrayList<Clientes> listadoClientes = Operaciones.obtenerClientes();
    //id dinamico para editar clientes
    @FXML
    int idCliente;

    //dato del input de busquedad de cliente
    @FXML
    TextField DatoBusquedad;

    //elemento del contenedor de card
    @FXML
    AnchorPane ContentCard;
    @FXML
    public void initialize() {
        //inicializar los contadores
        actualizarContadores();

        //renderizar los clientes en la tabla al cargar el controlador
        renderizarCardCliente();

        cargarListadoCliente_inputSelectDeuda();
        renderListUserInputAbono();
        //asignar el evento de busquedad de cliente
        DatoBusquedad.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                // Aquí llamas a la función que deseas ejecutar al presionar "Enter"
                buscarCliente();
                //limpiar el input
                DatoBusquedad.setText("");
            }
        });
    }

    public void actualizarContadores(){
        counterClientes.setText(String.valueOf(Operaciones.obtenerClientes().size()));
        counterDeuda.setText(String.valueOf(calcularDeudaTotal()));
        counterIngresos.setText(String.valueOf(calcularIngresos()));

        //actualizar el listado de clientes de el input select del registro de deuda
        cargarListadoCliente_inputSelectDeuda();
    }

    //ventana registro cliente
    @FXML
    public  void openCreateUser(){
        viewCrearUser.setVisible(true);
    }
    public  void cerrarCreateUser(){
        viewCrearUser.setVisible(false);
    }

    //view editar
    public void  openEditUser(Clientes datosCliente){
        nombreEditCliente.setText(datosCliente.getNombre());
        telefonoEditCliente.setText(datosCliente.getTelefono());
        cedulaEditCliente.setText(datosCliente.getCedula());
        comentarioEditCliente.setText(datosCliente.getComentario());

        viewEditar.setVisible(true);

        idCliente = datosCliente.getId();
    }

    public void closeEdituser( ){
        nombreEditCliente.setText("");
        telefonoEditCliente.setText("");
        cedulaEditCliente.setText("");
        comentarioEditCliente.setText("");

        viewEditar.setVisible(false);
    }
    //ventana abono
    public void openViewAbono(){
        viewAbono.setVisible(true);
    }

    public void openViewAbono(String nameUser){
        listadoClienteExistentesAbono.setValue(nameUser);
        viewAbono.setVisible(true);
    }

    //buscar cliente
    public void buscarCliente(){
        if (Objects.equals(DatoBusquedad.getText(),"")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("busquedad cliente");
            alert.setHeaderText(null);
            alert.setContentText("debe ingresar el nombre del cliente!");
            alert.showAndWait();
        }else {
            openResultadoBusquedad(Operaciones.buscarCliente(DatoBusquedad.getText()));
        }
    }
    //vista resultado de busquedad
    public void openResultadoBusquedad(Clientes datos){
        resultNombre.setText(datos.getNombre());
        resultComentario.setText(datos.getComentario());
        resultDataRegistro.setText(datos.getFecha_registro());
        resultCedula.setText(datos.getCedula());
        resultTelefono.setText(datos.getTelefono());
        resultDeudaPendiente.setText(String.valueOf(datos.getDeuda()));
        resultMontoAbonado.setText(String.valueOf(datos.getAbono()));

        viewBusquedad.setVisible(true);
    }

    public void closeResultadoBusquedad(){
        resultNombre.setText("");
        resultDataRegistro.setText("");
        resultMontoAbonado.setText("");
        resultCedula.setText("");
        resultTelefono.setText("");
        resultDeudaPendiente.setText("");
        resultComentario.setText("");

        viewBusquedad.setVisible(false);
    }

    public void cargarListadoCliente_inputSelectDeuda(){
        //renderizar todos los clientes en el campo de selection
        ObservableList<String> nombresClientes = FXCollections.observableArrayList();
        // Asignar los elementos al ChoiceBox
        for (Clientes clientes : listadoClientes){
            nombresClientes.add(clientes.getNombre());
        }
        listadoClienteExistentes.getItems().clear();

        listadoClienteExistentes.setItems(nombresClientes);

    }

    public void renderListUserInputAbono(){

        ObservableList<String> nombresClientes = FXCollections.observableArrayList();
        // Asignar los elementos al ChoiceBox
        for (Clientes clientes : listadoClientes){
            nombresClientes.add(clientes.getNombre());
        }
        listadoClienteExistentesAbono.getItems().clear();

        listadoClienteExistentesAbono.setItems(nombresClientes);
    }
    public void closeViewAbono(){
        viewAbono.setVisible(false);
    }

    //ventana deuda
    public void openViewDeuda(){
        viewDeuda.setVisible(true);
    }
    public void closeViewDeuda(){
        viewDeuda.setVisible(false);
    }

    //cambio de vistas
    public void cambioViewAjustes(){
        try {
            // Cargar la vista 2
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajustes-view.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la vista 2
            AjustesController controlador = loader.getController();
            controlador.setPrimaryStage(primaryStage);

            // Configurar la escena y mostrarla
            Scene scene = new Scene(root,1300, 680);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
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

    public void registrarCliente(){
        if (nombreNewCliente.getText().isEmpty() || telefonoNewCliente.getText().isEmpty()
                || cedulaNewCliente.getText().isEmpty() || comentarioNewCliente.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al registrar el cliente");
            alert.setHeaderText(null);
            alert.setContentText("Debe completar todos los datos.");
            alert.showAndWait();

        } else {
            // Crear el objeto del cliente y pasarlo por parámetro al método que lo registra en la BD
            Clientes newCliente = new Clientes(nombreNewCliente.getText(), cedulaNewCliente.getText(),
                    comentarioNewCliente.getText(), 0, 0, telefonoNewCliente.getText());
            //registrar
            Operaciones.registrarCLiente(newCliente);

            //limpiar los input
            nombreNewCliente.setText("");
            cedulaNewCliente.setText("");
            comentarioNewCliente.setText("");
            telefonoNewCliente.setText("");

            //cerrar ventana
            cerrarCreateUser();

            //actualizar la tabla de clientes

            renderizarCardCliente();

            actualizarContadores();
        }
    }

    public int calcularDeudaTotal(){
        int totalDeuda = 0;

        ArrayList<Clientes> listado =Operaciones.obtenerClientes();

        for(Clientes cliente : listado){
            totalDeuda += (int) cliente.getDeuda();
        }

        return totalDeuda;
    }
    public int calcularIngresos(){
        int totalIngresos = 0;

        ArrayList<Clientes> listado =Operaciones.obtenerClientes();

        for(Clientes cliente : listado){
            totalIngresos += (int) cliente.getAbono();
        }

        return totalIngresos;
    }

    public void registrarDeuda(){
        if(Objects.equals(listadoClienteExistentes.getValue(), "") || Objects.equals(montoDeuda.getText(), "")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("registro deuda");
            alert.setHeaderText(null);
            alert.setContentText("debe ingresar un monto y un cliente!");
            alert.showAndWait();
        }else {
            Operaciones.registrarDeuda(listadoClienteExistentes.getValue(),Float.parseFloat(montoDeuda.getText()));
            //cerrar la ventana y actualizar los contadores y la tabla


            renderizarCardCliente();

            actualizarContadores();

            //limpiar los input
            montoDeuda.setText("");
            closeViewDeuda();
        }


    }

    public void registrarAbono(){
        if(Objects.equals(listadoClienteExistentesAbono.getValue(), "") || Objects.equals(montoAbono.getText(), "")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("registro abono");
            alert.setHeaderText(null);
            alert.setContentText("debe ingresar un monto y un cliente!");
            alert.showAndWait();
        }else {
            Operaciones.registrarAbono(listadoClienteExistentesAbono.getValue(),Float.parseFloat(montoAbono.getText()));


            actualizarContadores();

            renderizarCardCliente();

            //limpiar los input
            montoAbono.setText("");

            closeViewAbono();
        }
    }

    //sobre cargo para registrar abono desde el cliente


    public void updateInfEdit(){
        if(Objects.equals(nombreEditCliente.getText(), "") || Objects.equals(telefonoEditCliente.getText(), "")
                || Objects.equals(cedulaEditCliente.getText(),"") || Objects.equals(comentarioEditCliente.getText(), "") ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("editar cliente");
            alert.setHeaderText(null);
            alert.setContentText("debe ingresar los datos a editar!");
            alert.showAndWait();
        }else {
            Operaciones.updateInfUser(idCliente,nombreEditCliente.getText(),telefonoEditCliente.getText(),cedulaEditCliente.getText(),comentarioEditCliente.getText());

            nombreEditCliente.setText("");
            telefonoEditCliente.setText("");
            cedulaEditCliente.setText("");
            comentarioEditCliente.setText("");

            renderizarCardCliente();

            actualizarContadores();

            closeEdituser();
        }
    }

    public Pane crearCardCliente(Clientes datos) {
        Pane root = new Pane();
        root.setPrefSize(301, 304);
        root.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 8;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(130, 126, 126, 0.46));
        root.setEffect(dropShadow);

        Pane bluePane = new Pane();
        bluePane.setPrefSize(301, 82);
        bluePane.setStyle("-fx-background-color:" + datos.getBacgroundColor() + ";");

        Label nameLabel = new Label(datos.getNombre());
        nameLabel.setLayoutX(10);
        nameLabel.setLayoutY(92);

        Label commentLabel = new Label(datos.getComentario());
        commentLabel.setLayoutX(10);
        commentLabel.setLayoutY(125);

        Label telLabel = new Label("Tel:");
        telLabel.setLayoutX(10);
        telLabel.setLayoutY(155);

        Label telValueLabel = new Label(datos.getTelefono());
        telValueLabel.setLayoutX(40);
        telValueLabel.setLayoutY(155);

        Separator separator = new Separator();
        separator.setLayoutX(33);
        separator.setLayoutY(193);
        separator.setPrefSize(234, 3);

        Label debtLabel = new Label("Deuda:");
        debtLabel.setLayoutX(92);
        debtLabel.setLayoutY(203);

        Label debtValueLabel = new Label(String.valueOf(datos.getDeuda()));
        debtValueLabel.setLayoutX(150);
        debtValueLabel.setLayoutY(203);
        debtValueLabel.setTextFill(Color.web("#df3131"));

        Button abonarButton = new Button("Abonar");
        abonarButton.setLayoutX(16);
        abonarButton.setLayoutY(246);
        abonarButton.setPrefSize(269, 45);
        abonarButton.setStyle("-fx-background-color: #13678A; -fx-border-radius: 5;");
        abonarButton.setTextFill(Color.WHITE);

        abonarButton.setOnAction(event -> {
            // Aquí llamas a la función de abono que deseas ejecutar
            openViewAbono(datos.getNombre());

        });

        // Crear un menú desplegable al hacer clic derecho
        ContextMenu contextMenu = new ContextMenu();

        // Crear las opciones del menú
        MenuItem detallesItem = new MenuItem("Detalles");
        MenuItem editarItem = new MenuItem("Editar");
        MenuItem eliminarItem = new MenuItem("Eliminar");

        // Agregar acciones a las opciones del menú
        detallesItem.setOnAction(event -> {
            // Aquí colocas la lógica para mostrar detalles del cliente
            openResultadoBusquedad(datos);
        });

        editarItem.setOnAction(event -> {
            // Aquí colocas la lógica para editar los datos del cliente
            openEditUser(datos);
        });

        eliminarItem.setOnAction(event -> {
            // Aquí colocas la lógica para eliminar el cliente
            Operaciones.eliminarClientes(datos.getId());
            actualizarContadores();
            renderizarCardCliente();
        });

        // Agregar las opciones al menú
        contextMenu.getItems().addAll(detallesItem, editarItem, eliminarItem);

        // Asignar el menú desplegable a la tarjeta de cliente
        root.setOnContextMenuRequested(event -> {
            contextMenu.show(root, event.getScreenX(), event.getScreenY());
        });

        root.getChildren().addAll(bluePane, nameLabel, commentLabel, telLabel, telValueLabel,
                separator, debtLabel, debtValueLabel, abonarButton);

        return root;
    }

    public void renderizarCardCliente() {
        double paneWidth = 301; // Ancho de cada tarjeta
        double paneHeight = 304; // Alto de cada tarjeta
        double margin = 20; // Margen entre las tarjetas y entre las columnas

        // Crear una lista de clientes (sustituye esta lista con tu lista real de clientes)
        List<Clientes> listaClientes = Operaciones.obtenerClientes();

        // Calcular la cantidad de filas y columnas
        int numColumnas = 3;
        int numFilas = (int) Math.ceil((double) listaClientes.size() / numColumnas);

        // Inicializar las posiciones iniciales
        double x = margin;
        double y = margin;

        // Verificar que ContentCard esté inicializado
        if (ContentCard != null) {
            // Limpiar ContentCard antes de agregar nuevas tarjetas
            ContentCard.getChildren().clear();

            // Iterar sobre la lista de clientes
            for (int i = 0; i < listaClientes.size(); i++) {
                Clientes cliente = listaClientes.get(i);
                Pane cardPane = crearCardCliente(cliente); // Método para crear una tarjeta de cliente (reemplázalo con tu propio método)

                // Establecer la posición de la tarjeta dentro de ContentCard
                AnchorPane.setLeftAnchor(cardPane, x);
                AnchorPane.setTopAnchor(cardPane, y);

                // Agregar la tarjeta a ContentCard
                ContentCard.getChildren().add(cardPane);

                // Calcular las nuevas posiciones
                if ((i + 1) % numColumnas == 0) {
                    // Si llegamos al final de una fila, moverse a la siguiente fila
                    x = margin;
                    y += paneHeight + margin;
                } else {
                    // De lo contrario, moverse a la siguiente columna
                    x += paneWidth + margin;
                }
            }
        } else {
            System.out.println("Error: ContentCard no está inicializado.");
        }
    }

}
