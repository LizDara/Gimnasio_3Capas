package com.example.gimnasio.CapaNegocio;

import com.example.gimnasio.CapaDatos.DAparato;

import java.util.List;
import java.util.Map;

public class NAparato {
    private DAparato dato;
    private int codigo;
    private String nombre;
    private String descripcion;
    private String estado;
    private int numero_sala;

    public NAparato() {
        dato = new DAparato();
    }

    public void insertarDatos(int codigo, String nombre, String descripcion, String estado, int numero_sala) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.numero_sala = numero_sala;
    }

    public void insertar() {
        dato.insertarDatos(codigo, nombre, descripcion, estado, numero_sala);
        dato.insertar();
    }

    public List<Map<String, Object>> listar() {
        return dato.listar();
    }

    public void editar() {
        dato.insertarDatos(codigo, nombre, descripcion, estado, numero_sala);
        dato.editar();
    }

    public void eliminar(int codigo) {
        dato.eliminar(codigo);
    }
}
