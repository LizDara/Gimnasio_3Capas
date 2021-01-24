package com.example.gimnasio.CapaDatos;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DDetalle {
    private static final String TAG = DDetalle.class.getSimpleName();

    private Conexion conexion;
    private int numero_clase;
    private int numero_nota;
    private float precio;
    private int cantidad;

    public DDetalle() {
        conexion = new Conexion();
    }

    public void insertarDatos(int numero_clase, int numero_nota, float precio, int cantidad) {
        this.numero_clase = numero_clase;
        this.numero_nota = numero_nota;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public void insertar(int numero_clase, int numero_nota, float precio, int cantidad) {
        try {
            Connection connection = conexion.open();
            String sql = "INSERT INTO detalle VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, numero_clase);
            statement.setInt(2, numero_nota);
            statement.setFloat(3, precio);
            statement.setInt(4, cantidad);
            statement.executeUpdate();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            conexion.close();
        }
    }

    public List<Map<String, Object>> listar(int numero_nota) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();

        try {
            Connection connection = conexion.open();
            String sql = "SELECT * FROM detalle WHERE numero_nota = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, numero_nota);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> params = new HashMap<>();
                params.put("numero_clase", resultSet.getInt("numero_clase"));
                params.put("numero_nota", resultSet.getInt("numero_nota"));
                params.put("precio", resultSet.getFloat("precio"));
                params.put("cantidad", resultSet.getInt("cantidad"));

                list.add(params);
            }
            return list;

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            conexion.close();
        }
        return null;
    }

    public void editar(int numero_clase, int numero_nota, float precio, int cantidad) {
        try {
            Connection connection = conexion.open();
            String sql = "UPDATE detalle SET precio = ?, cantidad = ? WHERE numero_clase = ? AND numero_nota = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setFloat(1, precio);
            statement.setInt(2, cantidad);
            statement.setInt(3, numero_clase);
            statement.setInt(4, numero_nota);
            statement.executeUpdate();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            conexion.close();
        }
    }

    public void eliminar(int numero_clase, int numero_nota) {
        try {
            Connection connection = conexion.open();
            String sql = "DELETE FROM detalle WHERE numero_clase = ? AND numero_nota = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, numero_clase);
            statement.setInt(2, numero_nota);
            statement.executeUpdate();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            conexion.close();
        }
    }
}
