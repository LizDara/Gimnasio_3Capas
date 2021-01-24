package com.example.gimnasio.CapaDatos;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private static final String TAG = Conexion.class.getSimpleName();

    private String host = "192.168.0.12";
    private String port = "3308";
    private String db = "gimnasio";
    private String user = "lizdara";
    private String password = "lizdara";
    private Connection connection;

    public Conexion () {
    }

    public Connection open() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection =
                    DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, password);//jdbc:mysql://192.168.0.12:3308/gimnasio
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}
