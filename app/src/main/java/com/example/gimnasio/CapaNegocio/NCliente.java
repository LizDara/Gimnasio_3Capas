package com.example.gimnasio.CapaNegocio;

import com.example.gimnasio.CapaDatos.DCliente;

import java.util.List;
import java.util.Map;

public class NCliente {
    private DCliente dato;
    private int ci;
    private String nombre;
    private String apellido;
    private String sexo;
    private int telefono;
    private String correo;

    public NCliente() {
        dato = new DCliente();
    }

    public void insertarDatos(int ci, String nombre, String apellido, String sexo, int telefono, String correo) {
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.telefono = telefono;
        this.correo = correo;
    }

    public void insertar() {
        dato.insertarDatos(ci, nombre, apellido, sexo, telefono, correo);
        dato.insertar();
    }

    public List<Map<String, Object>> listar() {
        return dato.listar();
    }

    public void editar() {
        dato.insertarDatos(ci, nombre, apellido, sexo, telefono, correo);
        dato.editar();
    }

    public void eliminar(int ci) {
        dato.eliminar(ci);
    }
}
