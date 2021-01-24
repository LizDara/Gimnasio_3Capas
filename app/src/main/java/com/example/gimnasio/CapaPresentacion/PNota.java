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
import com.example.gimnasio.CapaNegocio.NCliente;
import com.example.gimnasio.CapaNegocio.NNota;
import com.example.gimnasio.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PNota extends AppCompatActivity {
    @BindView(R.id.numero_nota)
    EditText numero_nota;
    @BindView(R.id.monto_nota)
    EditText monto_nota;
    @BindView(R.id.fecha_nota)
    EditText fecha_nota;
    @BindView(R.id.ci_cliente_nota)
    Spinner ci_cliente_nota;
    @BindView(R.id.numero_clase_detalle_1)
    Spinner numero_clase_detalle_1;
    @BindView(R.id.cantidad_detalle_1)
    EditText cantidad_detalle_1;
    @BindView(R.id.precio_detalle_1)
    EditText precio_detalle_1;
    @BindView(R.id.numero_clase_detalle_2)
    Spinner numero_clase_detalle_2;
    @BindView(R.id.cantidad_detalle_2)
    EditText cantidad_detalle_2;
    @BindView(R.id.precio_detalle_2)
    EditText precio_detalle_2;
    @BindView(R.id.numero_clase_detalle_3)
    Spinner numero_clase_detalle_3;
    @BindView(R.id.cantidad_detalle_3)
    EditText cantidad_detalle_3;
    @BindView(R.id.precio_detalle_3)
    EditText precio_detalle_3;
    @BindView(R.id.numero_clase_detalle_4)
    Spinner numero_clase_detalle_4;
    @BindView(R.id.cantidad_detalle_4)
    EditText cantidad_detalle_4;
    @BindView(R.id.precio_detalle_4)
    EditText precio_detalle_4;
    @BindView(R.id.numero_clase_detalle_5)
    Spinner numero_clase_detalle_5;
    @BindView(R.id.cantidad_detalle_5)
    EditText cantidad_detalle_5;
    @BindView(R.id.precio_detalle_5)
    EditText precio_detalle_5;
    @BindView(R.id.insertar_nota)
    Button insertar_nota;
    @BindView(R.id.modificar_nota)
    Button modificar_nota;
    @BindView(R.id.notas)
    RecyclerView notas;

    private NNota negocio;
    private NCliente negocioCliente;
    private NClase negocioClase;
    private int numero;
    private float monto;
    private String fecha;
    private int ci_cliente;
    private List<Map<String, Object>> listCliente;
    private List<Map<String, Object>> listClase;
    private List<Map<String, Object>> listDetalle;

    @OnClick(R.id.insertar_nota)
    public void onClickInsertarNota(View v) {
        insertarDatos();
        insertar();
    }

    @OnClick(R.id.modificar_nota)
    public void onClickModificarNota(View v) {
        insertarDatos();
        editar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_nota);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("NOTA");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        negocio = new NNota();
        negocioCliente = new NCliente();
        negocioClase = new NClase();

        listar();
        listarCliente();
        listarClase();
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
        listDetalle = new ArrayList<>();
        monto = 0;

        numero = Integer.parseInt(numero_nota.getText().toString());
        fecha = fecha_nota.getText().toString();
        ci_cliente = Integer.parseInt(listCliente.get(ci_cliente_nota.getSelectedItemPosition()).get("ci").toString());

        if (!cantidad_detalle_1.getText().toString().isEmpty() && !precio_detalle_1.getText().toString().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("numero_clase", Integer.parseInt(listClase.get(numero_clase_detalle_1.getSelectedItemPosition()).get("numero").toString()));
            param.put("precio", Float.parseFloat(precio_detalle_1.getText().toString()));
            param.put("cantidad", Integer.parseInt(cantidad_detalle_1.getText().toString()));
            monto += ((float)param.get("precio") * (int)param.get("cantidad"));
            listDetalle.add(param);
        }
        if (!cantidad_detalle_2.getText().toString().isEmpty() && !precio_detalle_2.getText().toString().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("numero_clase", Integer.parseInt(listClase.get(numero_clase_detalle_2.getSelectedItemPosition()).get("numero").toString()));
            param.put("precio", Float.parseFloat(precio_detalle_2.getText().toString()));
            param.put("cantidad", Integer.parseInt(cantidad_detalle_2.getText().toString()));
            monto += ((float)param.get("precio") * (int)param.get("cantidad"));
            listDetalle.add(param);
        }
        if (!cantidad_detalle_3.getText().toString().isEmpty() && !precio_detalle_3.getText().toString().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("numero_clase", Integer.parseInt(listClase.get(numero_clase_detalle_3.getSelectedItemPosition()).get("numero").toString()));
            param.put("precio", Float.parseFloat(precio_detalle_3.getText().toString()));
            param.put("cantidad", Integer.parseInt(cantidad_detalle_3.getText().toString()));
            monto += ((float)param.get("precio") * (int)param.get("cantidad"));
            listDetalle.add(param);
        }
        if (!cantidad_detalle_4.getText().toString().isEmpty() && !precio_detalle_4.getText().toString().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("numero_clase", Integer.parseInt(listClase.get(numero_clase_detalle_4.getSelectedItemPosition()).get("numero").toString()));
            param.put("precio", Float.parseFloat(precio_detalle_4.getText().toString()));
            param.put("cantidad", Integer.parseInt(cantidad_detalle_4.getText().toString()));
            monto += ((float)param.get("precio") * (int)param.get("cantidad"));
            listDetalle.add(param);
        }
        if (!cantidad_detalle_5.getText().toString().isEmpty() && !precio_detalle_5.getText().toString().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("numero_clase", Integer.parseInt(listClase.get(numero_clase_detalle_5.getSelectedItemPosition()).get("numero").toString()));
            param.put("precio", Float.parseFloat(precio_detalle_5.getText().toString()));
            param.put("cantidad", Integer.parseInt(cantidad_detalle_5.getText().toString()));
            monto += ((float)param.get("precio") * (int)param.get("cantidad"));
            listDetalle.add(param);
        }

        monto_nota.setText(String.valueOf(monto));
    }

    public void insertar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(numero, monto, fecha, ci_cliente);
                negocio.insertar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Map<String, Object> param : listDetalle)
                            insertarDetalle((int)param.get("numero_clase"), (float)param.get("precio"), (int)param.get("cantidad"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clean();
                                listar();
                            }
                        });
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
                        NotaAdapter notaAdapter = new NotaAdapter(getBaseContext(), list);
                        notas.setAdapter(notaAdapter);
                        notas.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }
                });
            }
        }).start();
    }

    public void editar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(numero, monto, fecha, ci_cliente);
                negocio.editar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Map<String, Object> param : listDetalle)
                            editarDetalle((int)param.get("numero_clase"), (float)param.get("precio"), (int)param.get("cantidad"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clean();
                                insertar_nota.setVisibility(View.VISIBLE);
                                modificar_nota.setVisibility(View.GONE);

                                listar();
                            }
                        });
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

    public void listarCliente() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listCliente = negocioCliente.listar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] numeros = new String[listCliente.size()];
                        for (int i = 0; i < numeros.length; i++)
                            numeros[i] = listCliente.get(i).get("nombre").toString() + " " + listCliente.get(i).get("apellido");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, numeros);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ci_cliente_nota.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    public void listarClase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listClase = negocioClase.listar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] numeros = new String[listClase.size()];
                        for (int i = 0; i < numeros.length; i++)
                            numeros[i] = listClase.get(i).get("nombre").toString() + "(" + listClase.get(i).get("precio").toString() + "Bs.)";
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, numeros);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        numero_clase_detalle_1.setAdapter(adapter);
                        numero_clase_detalle_2.setAdapter(adapter);
                        numero_clase_detalle_3.setAdapter(adapter);
                        numero_clase_detalle_4.setAdapter(adapter);
                        numero_clase_detalle_5.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    public void insertarDetalle(int numero_clase, float precio, int cantidad) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDetalle(numero_clase, precio, cantidad);
            }
        }).start();
    }

    public void listarDetalle(int numero) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listDetalle = negocio.listarDetalle(numero);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listDetalle.size() >= 1) {
                            int position = 0;
                            for (int i = 0; i < listClase.size(); i++) {
                                if (listClase.get(i).get("numero").toString().equals(listDetalle.get(0).get("numero_clase").toString()))
                                    position = i;
                            }
                            numero_clase_detalle_1.setSelection(position);
                            cantidad_detalle_1.setText(listDetalle.get(0).get("cantidad").toString());
                            precio_detalle_1.setText(listDetalle.get(0).get("precio").toString());
                        }
                        if (listDetalle.size() >= 2) {
                            int position = 0;
                            for (int i = 0; i < listClase.size(); i++) {
                                if (listClase.get(i).get("numero").toString().equals(listDetalle.get(1).get("numero_clase").toString()))
                                    position = i;
                            }
                            numero_clase_detalle_2.setSelection(position);
                            cantidad_detalle_2.setText(listDetalle.get(1).get("cantidad").toString());
                            precio_detalle_2.setText(listDetalle.get(1).get("precio").toString());
                        }
                        if (listDetalle.size() >= 3) {
                            int position = 0;
                            for (int i = 0; i < listClase.size(); i++) {
                                if (listClase.get(i).get("numero").toString().equals(listDetalle.get(2).get("numero_clase").toString()))
                                    position = i;
                            }
                            numero_clase_detalle_3.setSelection(position);
                            cantidad_detalle_3.setText(listDetalle.get(2).get("cantidad").toString());
                            precio_detalle_3.setText(listDetalle.get(2).get("precio").toString());
                        }
                        if (listDetalle.size() >= 4) {
                            int position = 0;
                            for (int i = 0; i < listClase.size(); i++) {
                                if (listClase.get(i).get("numero").toString().equals(listDetalle.get(3).get("numero_clase").toString()))
                                    position = i;
                            }
                            numero_clase_detalle_4.setSelection(position);
                            cantidad_detalle_4.setText(listDetalle.get(3).get("cantidad").toString());
                            precio_detalle_4.setText(listDetalle.get(3).get("precio").toString());
                        }
                        if (listDetalle.size() >= 5) {
                            int position = 0;
                            for (int i = 0; i < listClase.size(); i++) {
                                if (listClase.get(i).get("numero").toString().equals(listDetalle.get(4).get("numero_clase").toString()))
                                    position = i;
                            }
                            numero_clase_detalle_5.setSelection(position);
                            cantidad_detalle_5.setText(listDetalle.get(4).get("cantidad").toString());
                            precio_detalle_5.setText(listDetalle.get(4).get("precio").toString());
                        }
                    }
                });
            }
        }).start();
    }

    public void editarDetalle(int numero_clase, float precio, int cantidad) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.editarDetalle(numero_clase, precio, cantidad);
            }
        }).start();
    }

    public void clean() {
        numero_nota.setText("");
        numero_nota.setEnabled(true);
        monto_nota.setText("");
        fecha_nota.setText("");
        ci_cliente_nota.setSelection(0);
        numero_clase_detalle_1.setSelection(0);
        numero_clase_detalle_2.setSelection(0);
        numero_clase_detalle_3.setSelection(0);
        numero_clase_detalle_4.setSelection(0);
        numero_clase_detalle_5.setSelection(0);
        cantidad_detalle_1.setText("");
        cantidad_detalle_2.setText("");
        cantidad_detalle_3.setText("");
        cantidad_detalle_4.setText("");
        cantidad_detalle_5.setText("");
        precio_detalle_1.setText("");
        precio_detalle_2.setText("");
        precio_detalle_3.setText("");
        precio_detalle_4.setText("");
        precio_detalle_5.setText("");
    }

    public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public NotaAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.nota_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.numero_nota_list.setText(list.get(position).get("numero").toString());
            holder.monto_nota_list.setText(list.get(position).get("monto").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView numero_nota_list;
            TextView monto_nota_list;
            Button editar_nota;
            Button eliminar_nota;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                numero_nota_list = itemView.findViewById(R.id.numero_nota_list);
                monto_nota_list = itemView.findViewById(R.id.monto_nota_list);
                editar_nota = itemView.findViewById(R.id.editar_nota);
                eliminar_nota = itemView.findViewById(R.id.eliminar_nota);
                editar_nota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertar_nota.setVisibility(View.GONE);
                        modificar_nota.setVisibility(View.VISIBLE);

                        numero_nota.setText(param.get("numero").toString());
                        numero_nota.setEnabled(false);
                        monto_nota.setText(param.get("monto").toString());
                        int position = 0;
                        for (int i = 0; i < listCliente.size(); i++) {
                            if (listCliente.get(i).get("ci").toString().equals(param.get("ci_cliente").toString()))
                                position = i;
                        }
                        ci_cliente_nota.setSelection(position);
                        listarDetalle((int) param.get("numero"));
                    }
                });
                eliminar_nota.setOnClickListener(new View.OnClickListener() {
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
