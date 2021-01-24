package com.example.gimnasio.CapaNegocio;

import com.example.gimnasio.CapaDatos.DInstructor;

import java.util.List;
import java.util.Map;

public class NInstructor {
    private DInstructor dato;
    private int ci;
    private String nombre;
    private String apellido;
    private String sexo;
    private int telefono;
    private String direccion;

    public NInstructor() {
        dato = new DInstructor();
    }

    public void insertarDatos(int ci, String nombre, String apellido, String sexo, int telefono, String direccion) {
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public void insertar() {
        dato.insertarDatos(ci, nombre, apellido, sexo, telefono, direccion);
        dato.insertar();
    }

    public List<Map<String, Object>> listar() {
        return dato.listar();
    }

    public void editar() {
        dato.insertarDatos(ci, nombre, apellido, sexo, telefono, direccion);
        dato.editar();
    }

    public void eliminar(int ci) {
        dato.eliminar(ci);
    }
}
