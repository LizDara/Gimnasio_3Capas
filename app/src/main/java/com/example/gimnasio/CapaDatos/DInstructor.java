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

public class DInstructor {
    private static final String TAG = DInstructor.class.getSimpleName();

    private Conexion conexion;
    private int ci;
    private String nombre;
    private String apellido;
    private String sexo;
    private int telefono;
    private String direccion;

    public DInstructor() {
        conexion = new Conexion();
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
        try {
            Connection connection = conexion.open();
            String sql = "INSERT INTO instructor VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, ci);
            statement.setString(2, nombre);
            statement.setString(3, apellido);
            statement.setString(4, sexo);
            statement.setInt(5, telefono);
            statement.setString(6, direccion);
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
            String sql = "SELECT * FROM instructor";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Map<String, Object> params = new HashMap<>();
                params.put("ci", resultSet.getInt("ci"));
                params.put("nombre", resultSet.getString("nombre"));
                params.put("apellido", resultSet.getString("apellido"));
                params.put("sexo", resultSet.getString("sexo"));
                params.put("telefono", resultSet.getInt("telefono"));
                params.put("direccion", resultSet.getString("direccion"));

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
            String sql = "UPDATE instructor SET nombre = ?, apellido = ?, sexo = ?, telefono = ?, direccion = ? WHERE ci = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, sexo);
            statement.setInt(4, telefono);
            statement.setString(5, direccion);
            statement.setInt(6, ci);
            statement.executeUpdate();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            conexion.close();
        }
    }

    public void eliminar(int ci) {
        try {
            Connection connection = conexion.open();
            String sql = "DELETE FROM instructor WHERE ci = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, ci);
            statement.executeUpdate();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            conexion.close();
        }
    }
}
