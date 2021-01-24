package com.example.gimnasio.CapaPresentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gimnasio.CapaDatos.Conexion;
import com.example.gimnasio.CapaNegocio.NSala;
import com.example.gimnasio.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PSala extends AppCompatActivity {
    @BindView(R.id.dimension_sala)
    EditText dimension_sala;
    @BindView(R.id.ubicacion_sala)
    EditText ubicacion_sala;
    @BindView(R.id.insertar_sala)
    Button insertar_sala;
    @BindView(R.id.modificar_sala)
    Button modificar_sala;
    @BindView(R.id.salas)
    RecyclerView salas;

    private static final String TAG = PSala.class.getSimpleName();

    private NSala negocio;
    private int numero;
    private int dimension;
    private String ubicacion;

    @OnClick(R.id.insertar_sala)
    public void onClickInsertarSala(View v) {
        insertarDatos();
        insertar();
    }

    @OnClick(R.id.modificar_sala)
    public void onClickModificarSala(View v) {
        insertarDatos();
        editar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_sala);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("SALA");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        negocio = new NSala();

        listar();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertarDatos() {
        dimension = Integer.parseInt(dimension_sala.getText().toString());
        ubicacion = ubicacion_sala.getText().toString();
    }

    public void insertar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(dimension, ubicacion);
                negocio.insertar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dimension_sala.setText("");
                        ubicacion_sala.setText("");
                        listar();
                    }
                });
            }
        }).start();
    }

    public void listar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> list = negocio.listar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SalaAdapter salaAdapter = new SalaAdapter(getBaseContext(), list);
                        salas.setAdapter(salaAdapter);
                        salas.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }
                });
            }
        }).start();
    }

    public void editar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(dimension, ubicacion);
                negocio.editar(numero);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dimension_sala.setText("");
                        ubicacion_sala.setText("");
                        insertar_sala.setVisibility(View.VISIBLE);
                        modificar_sala.setVisibility(View.GONE);
                        listar();
                    }
                });
            }
        }).start();
    }

    public void eliminar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.eliminar(numero);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listar();
                    }
                });
            }
        }).start();
    }

    public class SalaAdapter extends RecyclerView.Adapter<SalaAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public SalaAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.sala_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.dimension_sala_list.setText(list.get(position).get("dimension").toString());
            holder.ubicacion_sala_list.setText(list.get(position).get("ubicacion").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView ubicacion_sala_list;
            TextView dimension_sala_list;
            Button editar_sala;
            Button eliminar_sala;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                ubicacion_sala_list = itemView.findViewById(R.id.ubicacion_sala_list);
                dimension_sala_list = itemView.findViewById(R.id.dimension_sala_list);
                editar_sala = itemView.findViewById(R.id.editar_sala);
                eliminar_sala = itemView.findViewById(R.id.eliminar_sala);
                editar_sala.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numero = (int) param.get("numero");

                        insertar_sala.setVisibility(View.GONE);
                        modificar_sala.setVisibility(View.VISIBLE);

                        dimension_sala.setText(param.get("dimension").toString());
                        ubicacion_sala.setText(param.get("ubicacion").toString());
                    }
                });
                eliminar_sala.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numero = (int) param.get("numero");
                        eliminar();
                    }
                });
            }
        }
    }

    class Task extends AsyncTask<Void, Void, Void> {
        String salas = "";
        String error = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //Class.forName("com.mysql.jdbc.Driver");
                //Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.12:3308/gimnasio", "lizdara", "lizdara");
                Conexion conexion = new Conexion();
                Connection connection = conexion.open();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM sala");

                while (resultSet.next()) {
                    salas += resultSet.getString(1) + " " + resultSet.getString(2) + resultSet.getString(3) + " " + "\n";
                }
                Log.i(TAG, salas);
            } catch (Exception e) {
                error = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (error != "")
                Log.i("Main", error);
            super.onPostExecute(aVoid);
        }
    }
}
