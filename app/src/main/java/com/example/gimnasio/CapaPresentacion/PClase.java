package com.example.gimnasio.CapaPresentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gimnasio.CapaNegocio.NClase;
import com.example.gimnasio.CapaNegocio.NInstructor;
import com.example.gimnasio.CapaNegocio.NSala;
import com.example.gimnasio.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PClase extends AppCompatActivity {
    @BindView(R.id.nombre_clase)
    EditText nombre_clase;
    @BindView(R.id.descripcion_clase)
    EditText descripcion_clase;
    @BindView(R.id.dia_clase)
    EditText dia_clase;
    @BindView(R.id.hora_clase)
    EditText hora_clase;
    @BindView(R.id.precio_clase)
    EditText precio_clase;
    @BindView(R.id.numero_sala_clase)
    Spinner numero_sala_clase;
    @BindView(R.id.ci_instructor_clase)
    Spinner ci_instructor_clase;
    @BindView(R.id.insertar_clase)
    Button insertar_clase;
    @BindView(R.id.modificar_clase)
    Button modificar_clase;
    @BindView(R.id.clases)
    RecyclerView clases;

    private static final String TAG = PClase.class.getSimpleName();

    private NClase negocio;
    private NSala negocioSala;
    private NInstructor negocioInstructor;
    private int numero;
    private String nombre;
    private String descripcion;
    private String dia;
    private String hora;
    private float precio;
    private int numero_sala;
    private int ci_instructor;
    private List<Map<String, Object>> listSala;
    private List<Map<String, Object>> listInstructor;

    @OnClick(R.id.insertar_clase)
    public void onClickInsertarClase(View v) {
        insertarDatos();
        insertar();
    }

    @OnClick(R.id.modificar_clase)
    public void onClickModificarClase(View v) {
        insertarDatos();
        editar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_clase);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("CLASE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        negocio = new NClase();
        negocioSala = new NSala();
        negocioInstructor = new NInstructor();

        listar();
        listarSala();
        listarInstructor();
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
        nombre = nombre_clase.getText().toString();
        descripcion = descripcion_clase.getText().toString();
        dia = dia_clase.getText().toString();
        hora = hora_clase.getText().toString();
        precio = Float.parseFloat(precio_clase.getText().toString());
        numero_sala = Integer.parseInt(listSala.get(numero_sala_clase.getSelectedItemPosition()).get("numero").toString());
        ci_instructor = Integer.parseInt(listInstructor.get(ci_instructor_clase.getSelectedItemPosition()).get("ci").toString());
    }

    public void insertar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(nombre, descripcion, dia, hora, precio, numero_sala, ci_instructor);
                negocio.insertar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nombre_clase.setText("");
                        descripcion_clase.setText("");
                        dia_clase.setText("");
                        hora_clase.setText("");
                        precio_clase.setText("");
                        numero_sala_clase.setSelection(0);
                        ci_instructor_clase.setSelection(0);
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
                        ClaseAdapter aparatoAdapter = new ClaseAdapter(getBaseContext(), list);
                        clases.setAdapter(aparatoAdapter);
                        clases.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }
                });
            }
        }).start();
    }

    public void editar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(nombre, descripcion, dia, hora, precio, numero_sala, ci_instructor);
                negocio.editar(numero);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nombre_clase.setText("");
                        descripcion_clase.setEnabled(true);
                        dia_clase.setText("");
                        hora_clase.setText("");
                        precio_clase.setText("");
                        numero_sala_clase.setSelection(0);
                        ci_instructor_clase.setSelection(0);
                        insertar_clase.setVisibility(View.VISIBLE);
                        modificar_clase.setVisibility(View.GONE);
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

    public void listarSala() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listSala = negocioSala.listar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] numeros = new String[listSala.size()];
                        for (int i = 0; i < numeros.length; i++)
                            numeros[i] = listSala.get(i).get("numero").toString() + " " + listSala.get(i).get("ubicacion");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, numeros);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        numero_sala_clase.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    public void listarInstructor() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listInstructor = negocioInstructor.listar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] nombres = new String[listInstructor.size()];
                        for (int i = 0; i < nombres.length; i++)
                            nombres[i] = listInstructor.get(i).get("nombre").toString() + " " + listInstructor.get(i).get("apellido");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, nombres);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ci_instructor_clase.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    public class ClaseAdapter extends RecyclerView.Adapter<ClaseAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public ClaseAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.clase_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.nombre_clase_list.setText(list.get(position).get("nombre").toString());
            holder.descripcion_clase_list.setText(list.get(position).get("descripcion").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView nombre_clase_list;
            TextView descripcion_clase_list;
            Button editar_clase;
            Button eliminar_clase;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre_clase_list = itemView.findViewById(R.id.nombre_clase_list);
                descripcion_clase_list = itemView.findViewById(R.id.descripcion_clase_list);
                editar_clase = itemView.findViewById(R.id.editar_clase);
                eliminar_clase = itemView.findViewById(R.id.eliminar_clase);
                editar_clase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numero = (int) param.get("numero");

                        insertar_clase.setVisibility(View.GONE);
                        modificar_clase.setVisibility(View.VISIBLE);

                        nombre_clase.setText(param.get("nombre").toString());
                        descripcion_clase.setText(param.get("descripcion").toString());
                        dia_clase.setText(param.get("dia").toString());
                        hora_clase.setText(param.get("hora").toString());
                        precio_clase.setText(param.get("precio").toString());
                        int position = 0;
                        for (int i = 0; i < listSala.size(); i++) {
                            if (listSala.get(i).get("numero").toString().equals(param.get("numero_sala").toString()))
                                position = i;
                        }
                        numero_sala_clase.setSelection(position);
                        for (int i = 0; i < listInstructor.size(); i++) {
                            if (listInstructor.get(i).get("ci").toString().equals(param.get("ci_instructor").toString()))
                                position = i;
                        }
                        ci_instructor_clase.setSelection(position);
                    }
                });
                eliminar_clase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numero = (int) param.get("numero");
                        eliminar();
                    }
                });
            }
        }
    }
}
