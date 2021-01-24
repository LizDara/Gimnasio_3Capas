package com.example.gimnasio.CapaPresentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gimnasio.CapaNegocio.NAparato;
import com.example.gimnasio.CapaNegocio.NSala;
import com.example.gimnasio.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PAparato extends AppCompatActivity {
    @BindView(R.id.codigo_aparato)
    EditText codigo_aparato;
    @BindView(R.id.nombre_aparato)
    EditText nombre_aparato;
    @BindView(R.id.descripcion_aparato)
    EditText descripcion_aparato;
    @BindView(R.id.estado_aparato)
    EditText estado_aparato;
    @BindView(R.id.numero_sala_aparato)
    Spinner numero_sala_aparato;
    @BindView(R.id.insertar_aparato)
    Button insertar_aparato;
    @BindView(R.id.modificar_aparato)
    Button modificar_aparato;
    @BindView(R.id.aparatos)
    RecyclerView aparatos;

    private static final String TAG = PAparato.class.getSimpleName();

    private NAparato negocio;
    private NSala negocioSala;
    private int codigo;
    private String nombre;
    private String descripcion;
    private String estado;
    private int numero_sala;
    private List<Map<String, Object>> listSala;

    @OnClick(R.id.insertar_aparato)
    public void onClickInsertarAparato(View v) {
        insertarDatos();
        insertar();
    }

    @OnClick(R.id.modificar_aparato)
    public void onClickModificarAparato(View v) {
        insertarDatos();
        editar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_aparato);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("APARATO");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        negocio = new NAparato();
        negocioSala = new NSala();

        listar();
        listarSala();
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
        codigo = Integer.parseInt(codigo_aparato.getText().toString());
        nombre = nombre_aparato.getText().toString();
        descripcion = descripcion_aparato.getText().toString();
        estado = estado_aparato.getText().toString();
        numero_sala = Integer.parseInt(listSala.get(numero_sala_aparato.getSelectedItemPosition()).get("numero").toString());
    }

    public void insertar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(codigo, nombre, descripcion, estado, numero_sala);
                negocio.insertar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        codigo_aparato.setText("");
                        nombre_aparato.setText("");
                        descripcion_aparato.setText("");
                        estado_aparato.setText("");
                        numero_sala_aparato.setSelection(0);
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
                        AparatoAdapter aparatoAdapter = new AparatoAdapter(getBaseContext(), list);
                        aparatos.setAdapter(aparatoAdapter);
                        aparatos.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }
                });
            }
        }).start();
    }

    public void editar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(codigo, nombre, descripcion, estado, numero_sala);
                negocio.editar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        codigo_aparato.setText("");
                        codigo_aparato.setEnabled(true);
                        nombre_aparato.setText("");
                        descripcion_aparato.setText("");
                        estado_aparato.setText("");
                        numero_sala_aparato.setSelection(0);
                        insertar_aparato.setVisibility(View.VISIBLE);
                        modificar_aparato.setVisibility(View.GONE);
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
                negocio.eliminar(codigo);

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
                        numero_sala_aparato.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    public class AparatoAdapter extends RecyclerView.Adapter<AparatoAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public AparatoAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.aparato_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.nombre_aparato_list.setText(list.get(position).get("nombre").toString());
            holder.descripcion_aparato_list.setText(list.get(position).get("descripcion").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView nombre_aparato_list;
            TextView descripcion_aparato_list;
            Button editar_aparato;
            Button eliminar_aparato;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre_aparato_list = itemView.findViewById(R.id.nombre_aparato_list);
                descripcion_aparato_list = itemView.findViewById(R.id.descripcion_aparato_list);
                editar_aparato = itemView.findViewById(R.id.editar_aparato);
                eliminar_aparato = itemView.findViewById(R.id.eliminar_aparato);
                editar_aparato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        codigo = (int) param.get("codigo");

                        insertar_aparato.setVisibility(View.GONE);
                        modificar_aparato.setVisibility(View.VISIBLE);

                        codigo_aparato.setText(param.get("codigo").toString());
                        codigo_aparato.setEnabled(false);
                        nombre_aparato.setText(param.get("nombre").toString());
                        descripcion_aparato.setText(param.get("descripcion").toString());
                        estado_aparato.setText(param.get("estado").toString());
                        int position = 0;
                        for (int i = 0; i < listSala.size(); i++) {
                            if (listSala.get(i).get("numero").toString().equals(param.get("numero_sala").toString()))
                                position = i;
                        }
                        numero_sala_aparato.setSelection(position);
                    }
                });
                eliminar_aparato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        codigo = (int) param.get("codigo");
                        eliminar();
                    }
                });
            }
        }
    }
}
