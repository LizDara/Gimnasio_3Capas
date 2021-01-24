package com.example.gimnasio.CapaNegocio;

import com.example.gimnasio.CapaDatos.DDetalle;
import com.example.gimnasio.CapaDatos.DNota;

import java.util.List;
import java.util.Map;

public class NNota {
    private DNota dato;
    private DDetalle datoDetalle;
    private int numero;
    private float monto;
    private String fecha;
    private int ci_cliente;

    public NNota() {
        dato = new DNota();
        datoDetalle = new DDetalle();
    }

    public void insertarDatos(int numero, float monto, String fecha, int ci_cliente) {
        this.numero = numero;
        this.monto = monto;
        this.fecha = fecha;
        this.ci_cliente = ci_cliente;
    }

    public void insertar() {
        dato.insertarDatos(numero, monto, fecha, ci_cliente);
        dato.insertar();
    }

    public List<Map<String, Object>> listar() {
        return dato.listar();
    }

    public void editar() {
        dato.insertarDatos(numero, monto, fecha, ci_cliente);
        dato.editar();
    }

    public void eliminar(int numero) {
        dato.eliminar(numero);
    }

    public void insertarDetalle(int numero_clase, float precio, int cantidad) {
        datoDetalle.insertar(numero_clase, numero, precio, cantidad);
    }

    public List<Map<String, Object>> listarDetalle(int numero) {
        return datoDetalle.listar(numero);
    }

    public void editarDetalle(int numero_clase, float precio, int cantidad) {
        datoDetalle.editar(numero_clase, numero, precio, cantidad);
    }

    public void eliminarDetalle(int numero_clase) {
        datoDetalle.eliminar(numero_clase, numero);
    }
}
