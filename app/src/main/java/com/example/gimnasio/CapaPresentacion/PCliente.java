package com.example.gimnasio.CapaPresentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gimnasio.CapaNegocio.NCliente;
import com.example.gimnasio.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PCliente extends AppCompatActivity {
    @BindView(R.id.ci_cliente)
    EditText ci_cliente;
    @BindView(R.id.nombre_cliente)
    EditText nombre_cliente;
    @BindView(R.id.apellido_cliente)
    EditText apellido_cliente;
    @BindView(R.id.femenino_cliente)
    RadioButton femenino_cliente;
    @BindView(R.id.masculino_cliente)
    RadioButton masculino_cliente;
    @BindView(R.id.telefono_cliente)
    EditText telefono_cliente;
    @BindView(R.id.correo_cliente)
    EditText correo_cliente;
    @BindView(R.id.insertar_cliente)
    Button insertar_cliente;
    @BindView(R.id.modificar_cliente)
    Button modificar_cliente;
    @BindView(R.id.clientes)
    RecyclerView clientes;

    private static final String TAG = PCliente.class.getSimpleName();

    private NCliente negocio;
    private int ci;
    private String nombre;
    private String apellido;
    private String sexo;
    private int telefono;
    private String correo;

    @OnClick(R.id.insertar_cliente)
    public void onClickInsertarCliente(View v) {
        insertarDatos();
        insertar();
    }

    @OnClick(R.id.modificar_cliente)
    public void onClickModificarCliente(View v) {
        insertarDatos();
        editar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_cliente);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("CLIENTE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        negocio = new NCliente();

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
        ci = Integer.parseInt(ci_cliente.getText().toString());
        nombre = nombre_cliente.getText().toString();
        apellido = apellido_cliente.getText().toString();
        sexo = (femenino_cliente.isChecked() ? "F" : "M");
        telefono = Integer.parseInt(telefono_cliente.getText().toString());
        correo = correo_cliente.getText().toString();
    }

    public void insertar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(ci, nombre, apellido, sexo, telefono, correo);
                negocio.insertar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ci_cliente.setText("");
                        nombre_cliente.setText("");
                        apellido_cliente.setText("");
                        femenino_cliente.setChecked(true);
                        telefono_cliente.setText("");
                        correo_cliente.setText("");
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
                        ClienteAdapter clienteAdapter = new ClienteAdapter(getBaseContext(), list);
                        clientes.setAdapter(clienteAdapter);
                        clientes.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }
                });
            }
        }).start();
    }

    public void editar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(ci, nombre, apellido, sexo, telefono, correo);
                negocio.editar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ci_cliente.setText("");
                        ci_cliente.setEnabled(true);
                        nombre_cliente.setText("");
                        apellido_cliente.setText("");
                        femenino_cliente.setChecked(true);
                        telefono_cliente.setText("");
                        correo_cliente.setText("");
                        insertar_cliente.setVisibility(View.VISIBLE);
                        modificar_cliente.setVisibility(View.GONE);
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
                negocio.eliminar(ci);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listar();
                    }
                });
            }
        }).start();
    }

    public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public ClienteAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.cliente_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.nombre_cliente_list.setText(list.get(position).get("nombre").toString());
            holder.telefono_cliente_list.setText(list.get(position).get("telefono").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView nombre_cliente_list;
            TextView telefono_cliente_list;
            Button editar_cliente;
            Button eliminar_cliente;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre_cliente_list = itemView.findViewById(R.id.nombre_cliente_list);
                telefono_cliente_list = itemView.findViewById(R.id.telefono_cliente_list);
                editar_cliente = itemView.findViewById(R.id.editar_cliente);
                eliminar_cliente = itemView.findViewById(R.id.eliminar_cliente);
                editar_cliente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ci = (int) param.get("ci");

                        insertar_cliente.setVisibility(View.GONE);
                        modificar_cliente.setVisibility(View.VISIBLE);

                        ci_cliente.setText(param.get("ci").toString());
                        ci_cliente.setEnabled(false);
                        nombre_cliente.setText(param.get("nombre").toString());
                        apellido_cliente.setText(param.get("apellido").toString());
                        if (param.get("sexo").toString().equals("F"))
                            femenino_cliente.setChecked(true);
                        else
                            masculino_cliente.setChecked(true);
                        telefono_cliente.setText(param.get("telefono").toString());
                        correo_cliente.setText(param.get("correo").toString());
                    }
                });
                eliminar_cliente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ci = (int) param.get("ci");
                        eliminar();
                    }
                });
            }
        }
    }
}
