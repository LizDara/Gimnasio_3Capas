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

public class DAparato {
    private static final String TAG = DAparato.class.getSimpleName();

    private Conexion conexion;
    private int codigo;
    private String nombre;
    private String descripcion;
    private String estado;
    private int numero_sala;

    public DAparato() {
        conexion = new Conexion();
    }

    public void insertarDatos(int codigo, String nombre, String descripcion, String estado, int numero_sala) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.numero_sala = numero_sala;
    }

    public void insertar() {
        try {
            Connection connection = conexion.open();
            String sql = "INSERT INTO aparato VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, codigo);
            statement.setString(2, nombre);
            statement.setString(3, descripcion);
            statement.setString(4, estado);
            statement.setInt(5, numero_sala);
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
            String sql = "SELECT * FROM aparato";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Map<String, Object> params = new HashMap<>();
                params.put("codigo", resultSet.getInt("codigo"));
                params.put("nombre", resultSet.getString("nombre"));
                params.put("descripcion", resultSet.getString("descripcion"));
                params.put("estado", resultSet.getString("estado"));
                params.put("numero_sala", resultSet.getInt("numero_sala"));

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
            String sql = "UPDATE aparato SET nombre = ?, descripcion = ?, estado = ?, numero_sala = ? WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setString(3, estado);
            statement.setInt(4, numero_sala);
            statement.setInt(5, codigo);
            statement.executeUpdate();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            conexion.close();
        }
    }

    public void eliminar(int codigo) {
        try {
            Connection connection = conexion.open();
            String sql = "DELETE FROM aparato WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, codigo);
            statement.executeUpdate();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            conexion.close();
        }
    }
}
