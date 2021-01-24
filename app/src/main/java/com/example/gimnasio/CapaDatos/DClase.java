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

public class DClase {
    private static final String TAG = DClase.class.getSimpleName();

    private Conexion conexion;
    private int numero;
    private String nombre;
    private String descripcion;
    private String dia;
    private String hora;
    private float precio;
    private int numero_sala;
    private int ci_instructor;

    public DClase() {
        conexion = new Conexion();
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
        try {
            Connection connection = conexion.open();
            String sql = "INSERT INTO clase VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setString(3, dia);
            statement.setString(4, hora);
            statement.setInt(5, numero_sala);
            statement.setInt(6, ci_instructor);
            statement.setFloat(7, precio);
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
            String sql = "SELECT * FROM clase";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Map<String, Object> params = new HashMap<>();
                params.put("numero", resultSet.getInt("numero"));
                params.put("nombre", resultSet.getString("nombre"));
                params.put("descripcion", resultSet.getString("descripcion"));
                params.put("dia", resultSet.getString("dia"));
                params.put("hora", resultSet.getString("hora"));
                params.put("precio", resultSet.getFloat("precio"));
                params.put("numero_sala", resultSet.getInt("numero_sala"));
                params.put("ci_instructor", resultSet.getInt("ci_instructor"));

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
            String sql = "UPDATE clase SET nombre = ?, descripcion = ?, dia = ?, hora = ?, numero_sala = ?, ci_instructor = ?, precio = ? WHERE numero = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setString(3, dia);
            statement.setString(4, hora);
            statement.setInt(5, numero_sala);
            statement.setInt(6, ci_instructor);
            statement.setFloat(7, precio);
            statement.setInt(8, numero);
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
            String sql = "DELETE FROM clase WHERE numero = ?";
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
