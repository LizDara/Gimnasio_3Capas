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

public class DSala {
    private static final String TAG = DSala.class.getSimpleName();

    private Conexion conexion;
    private int numero;
    private int dimension;
    private String ubicacion;

    public DSala() {
        conexion = new Conexion();
    }

    public void insertarDatos(int dimension, String ubicacion) {
        this.dimension = dimension;
        this.ubicacion = ubicacion;
    }

    public void insertar() {
        try {
            Connection connection = conexion.open();
            String sql = "INSERT INTO sala VALUES (null, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, dimension);
            statement.setString(2, ubicacion);
            statement.executeUpdate();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            conexion.close();
        }
    }

    public List<Map<String, Object>> listar() {
        ArrayList<Map<String, Object>> list = new ArrayList<>();

        try {
            Connection connection = conexion.open();
            String sql = "SELECT * FROM sala";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Log.i(TAG, resultSet.getString(1));
                Map<String, Object> params = new HashMap<>();
                params.put("numero", resultSet.getInt("numero"));
                params.put("dimension", resultSet.getInt("dimension"));
                params.put("ubicacion", resultSet.getString("ubicacion"));

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

    public void editar(int numero) {
        try {
            Connection connection = conexion.open();
            String sql = "UPDATE sala SET dimension = ?, ubicacion = ? WHERE numero = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, dimension);
            statement.setString(2, ubicacion);
            statement.setInt(3, numero);
            statement.executeUpdate();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            conexion.close();
        }
    }

    public void eliminar(int numero) {
        try {
            Connection connection = conexion.open();
            String sql = "DELETE FROM sala WHERE numero = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, numero);
            statement.executeUpdate();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            conexion.close();
        }
    }
}
