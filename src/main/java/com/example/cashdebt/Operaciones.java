package com.example.cashdebt;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import javafx.scene.control.Alert;

import java.sql.Connection;

import java.sql.SQLException;

public interface Operaciones  {

    String[] listColor = {"#425EAB", "#425EAB", "#A1AB42","#4798DF","#42AB99","#AB8C42","#E2AF37","#E26637","#8483820","#684335","#3F6835","#356568","#683535"};


    public static  String asignarColorFondoCliente(){
        // Crear un objeto Random para generar números aleatorios
        Random random = new Random();

        // Generar un número aleatorio en el rango del tamaño del array
        int indiceAleatorio = random.nextInt(listColor.length);

        // Obtener el elemento del array en la posición aleatoria generada
        String colorFondo = listColor[indiceAleatorio];

        // Devolver el elemento aleatorio
        return colorFondo;
    }

    //operaciones del cliente

    public static ArrayList<Clientes> obtenerClientes() {
        ArrayList<Clientes> listadoClientes = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            con = conetSQL.connect();
            if (con != null) {
                con.setAutoCommit(false); // Iniciar transacción
                statement = con.createStatement();
                String query = "SELECT * FROM clientes";
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    // Iterar y crear objetos Clientes a partir de los resultados
                    listadoClientes.add(new Clientes(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("cedula"),
                            resultSet.getString("comentario"),
                            resultSet.getFloat("deuda"),
                            resultSet.getFloat("abono"),
                            resultSet.getString("telefono"),
                            resultSet.getString("fondoColor"),
                            resultSet.getString("fecha_registro")
                    ));
                }
                con.commit(); // Confirmar transacción
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Deshacer transacción en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar recursos
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return listadoClientes;
    }

    public static void registrarCLiente(Clientes datosCLiente) {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = conetSQL.connect();
            if (con != null) {
                con.setAutoCommit(false); // Iniciar transacción
                String query = "SELECT * FROM clientes WHERE nombre = ?";
                statement = con.prepareStatement(query);
                statement.setString(1, datosCLiente.getNombre());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // Cliente ya existe, mostrar mensaje de error
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error al registrar el cliente");
                    alert.setHeaderText(null);
                    alert.setContentText("Ya existe un cliente registrado con este nombre!");
                    alert.showAndWait();
                } else {
                    // Insertar nuevo cliente

                    String query2 = "INSERT INTO clientes (nombre, cedula, comentario, deuda, abono, telefono, fondoColor, fecha_registro) VALUES ('" +
                            datosCLiente.getNombre() + "', '" + datosCLiente.getCedula() + "', '" + datosCLiente.getComentario() + "', " +
                            datosCLiente.getDeuda() + ", " + datosCLiente.getAbono() + ", '" + datosCLiente.getTelefono() + "', '" +
                            datosCLiente.getBacgroundColor() + "', '" + datosCLiente.getFecha_registro() + "')";

                    statement = con.prepareStatement(query2);

                    statement.executeUpdate();
                    con.commit();
                    // Confirmar transacción
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registro de cliente");
                    alert.setHeaderText(null);
                    alert.setContentText("Se registró el cliente correctamente!");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Deshacer transacción en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al registrar el cliente");
            alert.setHeaderText(null);
            alert.setContentText("El cliente no se pudo registrar correctamente!");
            alert.showAndWait();
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar recursos
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void eliminarClientes(int id) {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = conetSQL.connect();
            if (con != null) {
                con.setAutoCommit(false); // Iniciar transacción
                String query = "DELETE FROM clientes WHERE id = ?";
                statement = con.prepareStatement(query);
                statement.setInt(1, id);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 1) {
                    con.commit();
                    // Confirmar transacción
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Eliminar cliente");
                    alert.setHeaderText(null);
                    alert.setContentText("El cliente fue eliminado correctamente!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error al eliminar el cliente");
                    alert.setHeaderText(null);
                    alert.setContentText("El cliente no existe!");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Deshacer transacción en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al eliminar el cliente");
            alert.setHeaderText(null);
            alert.setContentText("El cliente no se pudo eliminar correctamente!");
            alert.showAndWait();
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar recursos
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void registrarDeuda(String cliente, float montoDeuda) {
        Connection con = null;
        Statement statement = null;
        try {
            con = conetSQL.connect();
            if (con != null) {
                con.setAutoCommit(false); // Iniciar transacción
                statement = con.createStatement();

                // Consultar si existe un cliente con el mismo nombre
                String query = "SELECT * FROM clientes WHERE nombre = '" + cliente + "'";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    // Obtener el valor de la deuda del cliente para actualizarla con el nuevo valor
                    float deudaActual = resultSet.getFloat("deuda");

                    // Actualizar la deuda del cliente
                    float nuevaDeuda = deudaActual + montoDeuda;
                    String updateQuery = "UPDATE clientes SET deuda = " + nuevaDeuda + " WHERE nombre = '" + cliente + "'";
                    statement.executeUpdate(updateQuery);

                    con.commit();
                    // Confirmar transacción
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registro de deuda");
                    alert.setHeaderText(null);
                    alert.setContentText("La deuda fue registrada correctamente!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error al registrar la deuda");
                    alert.setHeaderText(null);
                    alert.setContentText("No se encontró el cliente especificado!");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Deshacer transacción en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error al registrar la deuda");
            alert.setHeaderText(null);
            alert.setContentText("Ocurrió un error durante la operación!");
            alert.showAndWait();
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar recursos
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void registrarAbono(String cliente, float montoAbono) {
        Connection con = null;
        PreparedStatement deudaStatement = null;
        PreparedStatement abonoStatement = null;
        PreparedStatement updateAbonoStatement = null;
        PreparedStatement updateDeudaStatement = null;
        try {
            con = conetSQL.connect();
            if (con != null) {
                con.setAutoCommit(false); // Iniciar transacción

                // Preparar la consulta para obtener la deuda actual del cliente
                String deudaQuery = "SELECT deuda FROM clientes WHERE nombre = ?";
                deudaStatement = con.prepareStatement(deudaQuery);
                deudaStatement.setString(1, cliente);
                ResultSet deudaResultSet = deudaStatement.executeQuery();

                if (deudaResultSet.next()) {
                    float deudaActual = deudaResultSet.getFloat("deuda");

                    // Validar el monto del abono
                    if (montoAbono < 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Ingrese un monto de abono válido");
                        alert.showAndWait();
                    } else if (montoAbono > deudaActual) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("El monto abonado no puede ser mayor que la deuda del cliente!");
                        alert.showAndWait();
                    } else {
                        // Preparar la consulta para actualizar el abono del cliente
                        String abonoQuery = "SELECT abono FROM clientes WHERE nombre = ?";
                        abonoStatement = con.prepareStatement(abonoQuery);
                        abonoStatement.setString(1, cliente);
                        ResultSet abonoResultSet = abonoStatement.executeQuery();

                        if (abonoResultSet.next()) {
                            float totalAbono = abonoResultSet.getFloat("abono");
                            float nuevoAbono = totalAbono + montoAbono;

                            // Preparar la consulta para actualizar el abono del cliente
                            String updateAbonoQuery = "UPDATE clientes SET abono = ? WHERE nombre = ?";
                            updateAbonoStatement = con.prepareStatement(updateAbonoQuery);
                            updateAbonoStatement.setFloat(1, nuevoAbono);
                            updateAbonoStatement.setString(2, cliente);
                            updateAbonoStatement.executeUpdate();

                            // Calcular y actualizar la nueva deuda del cliente
                            float nuevaDeuda = deudaActual - montoAbono;
                            String updateDeudaQuery = "UPDATE clientes SET deuda = ? WHERE nombre = ?";
                            updateDeudaStatement = con.prepareStatement(updateDeudaQuery);
                            updateDeudaStatement.setFloat(1, nuevaDeuda);
                            updateDeudaStatement.setString(2, cliente);
                            updateDeudaStatement.executeUpdate();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Éxito");
                            alert.setHeaderText(null);
                            alert.setContentText("El abono ha sido registrado correctamente!");
                            alert.showAndWait();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No se encontró al cliente!");
                    alert.showAndWait();
                }

                con.commit(); // Confirmar transacción
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Deshacer transacción en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ocurrió un error durante la operación!");
            alert.showAndWait();
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar recursos
            if (deudaStatement != null) {
                try {
                    deudaStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (abonoStatement != null) {
                try {
                    abonoStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (updateAbonoStatement != null) {
                try {
                    updateAbonoStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (updateDeudaStatement != null) {
                try {
                    updateDeudaStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void updateInfUser(int idCliente, String nombre, String telefono, String cedula, String comentario) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = conetSQL.connect();
            if (con != null) {
                con.setAutoCommit(false); // Iniciar transacción
                String query = "UPDATE clientes SET nombre = ?, telefono = ?, cedula = ?, comentario = ? WHERE id = ?";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, telefono);
                preparedStatement.setString(3, cedula);
                preparedStatement.setString(4, comentario);
                preparedStatement.setInt(5, idCliente);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Actualización exitosa");
                    alert.setHeaderText(null);
                    alert.setContentText("Los datos del cliente fueron actualizados correctamente!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error al actualizar");
                    alert.setHeaderText(null);
                    alert.setContentText("No se encontró ningún cliente con el ID proporcionado!");
                    alert.showAndWait();
                }
                con.commit(); // Confirmar transacción
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Deshacer transacción en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al actualizar");
            alert.setHeaderText(null);
            alert.setContentText("Ocurrió un error durante la operación!");
            alert.showAndWait();
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar recursos
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Clientes buscarCliente(String nombre) {
        Clientes cliente = null;
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            con = conetSQL.connect();
            if (con != null) {
                con.setAutoCommit(false); // Iniciar transacción
                String query = "SELECT * FROM clientes WHERE nombre = ?";
                statement = con.prepareStatement(query);
                statement.setString(1, nombre);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    cliente = new Clientes(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("cedula"),
                            resultSet.getString("comentario"),
                            resultSet.getFloat("deuda"),
                            resultSet.getFloat("abono"),
                            resultSet.getString("telefono"),
                            resultSet.getString("fondoColor"),
                            resultSet.getString("fecha_registro")
                    );

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Búsqueda de cliente");
                    alert.setHeaderText(null);
                    alert.setContentText("Cliente encontrado correctamente!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No se encontró ningún cliente con este nombre!");
                    alert.showAndWait();
                }
                con.commit(); // Confirmar transacción
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Deshacer transacción en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ocurrió un error durante la operación!");
            alert.showAndWait();
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar recursos
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return cliente;
    }


    public static void actualizarUsuario(String newNameUser, String newPass, String oldPass) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            con = conetSQL.connect();
            if (con != null) {
                con.setAutoCommit(false); // Iniciar transacción
                // Verificar que la contraseña anterior sea la correcta
                String query = "SELECT * FROM ajustes";
                statement = con.prepareStatement(query);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String currentPass = resultSet.getString("clave");
                    if (!Objects.equals(currentPass, oldPass)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error al actualizar");
                        alert.setHeaderText(null);
                        alert.setContentText("La clave no coincide con la actual!");
                        alert.showAndWait();
                    } else {
                        String updateQuery = "UPDATE ajustes SET usuario = ?, clave = ?";
                        statement = con.prepareStatement(updateQuery);
                        statement.setString(1, newNameUser);
                        statement.setString(2, newPass);
                        int rowsAffected = statement.executeUpdate();
                        if (rowsAffected > 0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Actualizar usuario");
                            alert.setHeaderText(null);
                            alert.setContentText("Se actualizaron los datos del usuario!");
                            alert.showAndWait();
                        }
                    }
                }
                con.commit(); // Confirmar transacción
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Deshacer transacción en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al actualizar");
            alert.setHeaderText(null);
            alert.setContentText("Ocurrió un error durante la operación");
            alert.showAndWait();
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar recursos
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
