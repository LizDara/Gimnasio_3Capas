package com.example.gimnasio.CapaNegocio;

import com.example.gimnasio.CapaDatos.DSala;

import java.util.List;
import java.util.Map;

public class NSala {
    private DSala dato;
    private int numero;
    private int dimension;
    private String ubicacion;

    public NSala() {
        dato = new DSala();
    }

    public void insertarDatos(int dimension, String ubicacion) {
        this.dimension = dimension;
        this.ubicacion = ubicacion;
    }

    public void insertar() {
        dato.insertarDatos(dimension, ubicacion);
        dato.insertar();
    }

    public List<Map<String, Object>> listar() {
        return dato.listar();
    }

    public void editar(int numero) {
        dato.insertarDatos(dimension, ubicacion);
        dato.editar(numero);
    }

    public void eliminar(int numero) {
        dato.eliminar(numero);
    }
}
