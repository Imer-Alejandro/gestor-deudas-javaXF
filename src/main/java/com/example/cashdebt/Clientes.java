package com.example.cashdebt;

import java.time.LocalDate;

public class Clientes implements Operaciones{

    int id;
    String Nombre;
    String cedula;
    String comentario;

    float totalDeuda;
    float Deuda;
    float Abono;
    String Telefono;

    String bacgroundColor;

    String fecha_registro;

    public Clientes(int id, String Nombre, String cedula, String comentario, float Deuda, float Abono, String Telefono, String fondoColor, String fechaRegistro){
        this.id=id;
        this.Nombre = Nombre;
        this.cedula = cedula;
        this.comentario = comentario;
        this.Deuda = Deuda;
        this.Abono = Abono;
        this.Telefono = Telefono;
        this.bacgroundColor = fondoColor;
        this.fecha_registro= fechaRegistro;
    }

    public Clientes(String Nombre, String cedula,String comentario,float Deuda,float Abono,String Telefono){
        this.Nombre = Nombre;
        this.cedula = cedula;
        this.comentario = comentario;
        this.Deuda = Deuda;
        this.Abono = Abono;
        this.Telefono = Telefono;
        this.bacgroundColor = Operaciones.asignarColorFondoCliente();
        this.fecha_registro= String.valueOf(LocalDate.now());
    }

    public int getId(){
        return id;
    }

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public String getComentario() {
        return comentario;
    }

    public void setComentario (String comentario) {
        this.comentario = comentario;
    }
    public float getDeuda() {
        return Deuda;
    }
    public void setDeudad(float Deuda) {
        this.Deuda = Deuda;
    }
    public float getAbono() {
        return Abono;
    }
    public void setAbono(float Abono) {
        this.Abono = Abono;
    }
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getBacgroundColor(){
        return  bacgroundColor;
    }
    public String getFecha_registro(){
        return  fecha_registro;
    }

}
