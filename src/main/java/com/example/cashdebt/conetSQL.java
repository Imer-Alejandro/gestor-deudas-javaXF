package com.example.cashdebt;

import java.sql.*;

public class conetSQL {
    public static Connection connect() {
        Connection conn = null;
        try {
            // Establecer la conexión a la base de datos SQLite
            conn = DriverManager.getConnection("jdbc:sqlite:base_de_datos.db");
            System.out.println("Conexión a SQLite establecida");

            // Verificar si la tabla datos existe, si no, crearla
            Statement statement = conn.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS datos (identificador INTEGER PRIMARY KEY, contenido TEXT)";
            statement.execute(createTableSQL);
            System.out.println("Tabla datos creada o ya existente.");
            statement.close();

            // Verificar si la tabla clientes existe, si no, crearla
            statement = conn.createStatement();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, null, "clientes", null);
            if (!tableResultSet.next()) {
                // La tabla 'clientes' no existe, así que la creamos
                String createTableQuery = "CREATE TABLE clientes (\n" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    nombre TEXT NOT NULL,\n" +
                        "    cedula TEXT,\n" +
                        "    comentario TEXT,\n" +
                        "    deuda FLOAT,\n" +
                        "    abono FLOAT,\n" +
                        "    telefono TEXT,\n" +
                        "    fondoColor TEXT,\n" +
                        "    fecha_registro TEXT\n" +
                        ");";
                statement.execute(createTableQuery);
                System.out.println("Tabla clientes creada.");
            }
            // Cerrar recursos
            tableResultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar o crear la tabla: " + e.getMessage());
        }
        return conn;
    }
}
