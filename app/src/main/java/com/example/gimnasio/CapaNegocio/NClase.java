package com.example.gimnasio.CapaNegocio;

import com.example.gimnasio.CapaDatos.DClase;

import java.util.List;
import java.util.Map;

public class NClase {
    private DClase dato;
    private int numero;
    private String nombre;
    private String descripcion;
    private String dia;
    private String hora;
    private float precio;
    private int numero_sala;
    private int ci_instructor;

    public NClase() {
        dato = new DClase();
    }

    public void insertarDatos(String nombre, String descripcion, String dia, String hora, float precio, int numero_sala, int ci_instructor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dia = dia;
        this.hora = hora;
        this.precio = precio;
        this.numero_sala = numero_sala;
        this.ci_instructor = ci_instructor;
    }

    public void insertar() {
        dato.insertarDatos(nombre, descripcion, dia, hora, precio, numero_sala, ci_instructor);
        dato.insertar();
    }

    public List<Map<String, Object>> listar() {
        return dato.listar();
    }

    public void editar(int numero) {
        dato.insertarDatos(nombre, descripcion, dia, hora, precio, numero_sala, ci_instructor);
        dato.editar(numero);
    }

    public void eliminar(int numero) {
        dato.eliminar(numero);
    }
}
