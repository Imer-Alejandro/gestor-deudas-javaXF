package com.example.cashdebt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Objects;

public class InicioController implements Operaciones {
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
    ChoiceBox<String>  listadoClienteExistentesAbono;


    //datos para el registro de deuda
    @FXML
    TextField montoDeuda;
    @FXML
    ChoiceBox<String> listadoClienteExistentes;

    //datos de la tabla
    @FXML
    TableView<Clientes> tablaClientes;
    @FXML
    TableColumn<Clientes, Integer> idClienteColumn;
    @FXML
    TableColumn<Clientes, String> cedulaColumn;
    @FXML
    TableColumn<Clientes, String> nombreCompletoColumn;
    @FXML
    TableColumn<Clientes, String> fechaRegistroColumn;
    @FXML
    TableColumn<Clientes, Float> deudaColumn;
    @FXML
    TableColumn<Clientes, Float> abonadoColumn;
    @FXML
    TableColumn<Clientes, String> comentarioColumn;

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
    @FXML
    public void initialize() {
        //inicializar los contadores
        actualizarContadores();

        //renderizar los clientes en la tabla al cargar el controlador
        renderizarTablaCliente();

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


    //registrar nuevo cliente
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
            renderizarTablaCliente();

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

    //metodo para renderizar la tabla
    public void renderizarTablaCliente(){
        // Configurar las propiedades de las columnas
        idClienteColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cedulaColumn.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        nombreCompletoColumn.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        fechaRegistroColumn.setCellValueFactory(new PropertyValueFactory<>("Fecha_registro"));
        deudaColumn.setCellValueFactory(new PropertyValueFactory<>("Deuda"));
        abonadoColumn.setCellValueFactory(new PropertyValueFactory<>("Abono"));
        comentarioColumn.setCellValueFactory(new PropertyValueFactory<>("comentario"));


        // Obtener la lista de clientes
        ArrayList<Clientes> clientes = Operaciones.obtenerClientes();

        // Crear un ObservableList a partir de la lista de clientes
        ObservableList<Clientes> listaClientes = FXCollections.observableArrayList(clientes);

        // Limpiar la tabla antes de agregar nuevas filas
        tablaClientes.getItems().clear();

        // Asignar la lista de clientes como el modelo de datos de la tabla
        tablaClientes.setItems(listaClientes);
        // Crear el menú contextual
        ContextMenu contextMenu = new ContextMenu();
        MenuItem detallesMenuItem = new MenuItem("detalles");
        MenuItem abonarMenuItem = new MenuItem("abonar");
        MenuItem editMenuItem = new MenuItem("Editar");
        MenuItem deleteMenuItem = new MenuItem("Eliminar");




        abonarMenuItem.setOnAction(event -> {
            Clientes selectedCliente = tablaClientes.getSelectionModel().getSelectedItem();
            if (selectedCliente != null) {
                openViewAbono(selectedCliente.getNombre());
            }
        });

        // Asignar acciones a los elementos del menú contextual
        detallesMenuItem.setOnAction(event -> {
            Clientes selectedCliente = tablaClientes.getSelectionModel().getSelectedItem();
            if (selectedCliente != null) {
                openResultadoBusquedad(selectedCliente);
            }
        });

        deleteMenuItem.setOnAction(event -> {
            Clientes selectedCliente = tablaClientes.getSelectionModel().getSelectedItem();
            if (selectedCliente != null) {
                // Realizar la operación de eliminación
                Operaciones.eliminarClientes(selectedCliente.getId());
                actualizarContadores();
                renderizarTablaCliente();
            }
        });

        editMenuItem.setOnAction(event -> {
            Clientes selectedCliente = tablaClientes.getSelectionModel().getSelectedItem();
            if (selectedCliente != null) {
                openEditUser(selectedCliente);
            }
        });

        // Agregar los elementos al menú contextual
        contextMenu.getItems().addAll(detallesMenuItem,abonarMenuItem,editMenuItem,deleteMenuItem );

        // Asignar el menú contextual a la TableView
        tablaClientes.setContextMenu(contextMenu);

        // Manejar el evento de clic derecho para mostrar el menú contextual
        tablaClientes.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) { // Verificar si el clic fue con el botón derecho del mouse
                contextMenu.show(tablaClientes, event.getScreenX(), event.getScreenY());
            }
        });
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

            renderizarTablaCliente();
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

            renderizarTablaCliente();
            actualizarContadores();


            //limpiar los input
            montoAbono.setText("");

            closeViewAbono();
        }
    }

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

            renderizarTablaCliente();
            actualizarContadores();

            closeEdituser();
        }
    }

}
