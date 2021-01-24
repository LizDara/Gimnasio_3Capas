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

public class DNota {
    private static final String TAG = DNota.class.getSimpleName();

    private Conexion conexion;
    private int numero;
    private float monto;
    private String fecha;
    private int ci_cliente;

    public DNota() {
        conexion = new Conexion();
    }

    public void insertarDatos(int numero, float monto, String fecha, int ci_cliente) {
        this.numero = numero;
        this.monto = monto;
        this.fecha = fecha;
        this.ci_cliente = ci_cliente;
    }

    public void insertar() {
        try {
            Connection connection = conexion.open();
            String sql = "INSERT INTO nota VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, numero);
            statement.setFloat(2, monto);
            statement.setString(3, fecha);
            statement.setInt(4, ci_cliente);
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
            String sql = "SELECT * FROM nota";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Map<String, Object> params = new HashMap<>();
                params.put("numero", resultSet.getInt("numero"));
                params.put("monto", resultSet.getFloat("monto"));
                params.put("fecha", resultSet.getString("fecha"));
                params.put("ci_cliente", resultSet.getInt("ci_cliente"));

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

    public void editar() {
        try {
            Connection connection = conexion.open();
            String sql = "UPDATE nota SET monto = ?, fecha = ?, ci_cliente = ? WHERE numero = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setFloat(1, monto);
            statement.setString(2, fecha);
            statement.setInt(3, ci_cliente);
            statement.setInt(4, numero);
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
            String sql = "DELETE FROM nota WHERE numero = ?";
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
