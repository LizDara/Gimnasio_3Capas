package com.example.gimnasio.CapaPresentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gimnasio.CapaNegocio.NInstructor;
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

public class PInstructor extends AppCompatActivity {
    @BindView(R.id.ci_instructor)
    EditText ci_instructor;
    @BindView(R.id.nombre_instructor)
    EditText nombre_instructor;
    @BindView(R.id.apellido_instructor)
    EditText apellido_instructor;
    @BindView(R.id.femenino_instructor)
    RadioButton femenino_instructor;
    @BindView(R.id.masculino_instructor)
    RadioButton masculino_instructor;
    @BindView(R.id.telefono_instructor)
    EditText telefono_instructor;
    @BindView(R.id.direccion_instructor)
    EditText direccion_instructor;
    @BindView(R.id.insertar_instructor)
    Button insertar_instructor;
    @BindView(R.id.modificar_instructor)
    Button modificar_instructor;
    @BindView(R.id.instructores)
    RecyclerView instructores;

    private static final String TAG = PInstructor.class.getSimpleName();

    private NInstructor negocio;
    private int ci;
    private String nombre;
    private String apellido;
    private String sexo;
    private int telefono;
    private String direccion;

    @OnClick(R.id.insertar_instructor)
    public void onClickInsertarInstructor(View v) {
        insertarDatos();
        insertar();
    }

    @OnClick(R.id.modificar_instructor)
    public void onClickModificarInstructor(View v) {
        insertarDatos();
        editar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_instructor);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("INSTRUCTOR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        negocio = new NInstructor();

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
        ci = Integer.parseInt(ci_instructor.getText().toString());
        nombre = nombre_instructor.getText().toString();
        apellido = apellido_instructor.getText().toString();
        sexo = (femenino_instructor.isChecked() ? "F" : "M");
        telefono = Integer.parseInt(telefono_instructor.getText().toString());
        direccion = direccion_instructor.getText().toString();
    }

    public void insertar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(ci, nombre, apellido, sexo, telefono, direccion);
                negocio.insertar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ci_instructor.setText("");
                        nombre_instructor.setText("");
                        apellido_instructor.setText("");
                        femenino_instructor.setChecked(true);
                        telefono_instructor.setText("");
                        direccion_instructor.setText("");
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
                        InstructorAdapter instructorAdapter = new InstructorAdapter(getBaseContext(), list);
                        instructores.setAdapter(instructorAdapter);
                        instructores.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }
                });
            }
        }).start();
    }

    public void editar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                negocio.insertarDatos(ci, nombre, apellido, sexo, telefono, direccion);
                negocio.editar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ci_instructor.setText("");
                        ci_instructor.setEnabled(true);
                        nombre_instructor.setText("");
                        apellido_instructor.setText("");
                        femenino_instructor.setChecked(true);
                        telefono_instructor.setText("");
                        direccion_instructor.setText("");
                        insertar_instructor.setVisibility(View.VISIBLE);
                        modificar_instructor.setVisibility(View.GONE);
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

    public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public InstructorAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.instructor_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.nombre_instructor_list.setText(list.get(position).get("nombre").toString());
            holder.telefono_instructor_list.setText(list.get(position).get("telefono").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView nombre_instructor_list;
            TextView telefono_instructor_list;
            Button editar_instructor;
            Button eliminar_instructor;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre_instructor_list = itemView.findViewById(R.id.nombre_instructor_list);
                telefono_instructor_list = itemView.findViewById(R.id.telefono_instructor_list);
                editar_instructor = itemView.findViewById(R.id.editar_instructor);
                eliminar_instructor = itemView.findViewById(R.id.eliminar_instructor);
                editar_instructor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ci = (int) param.get("ci");

                        insertar_instructor.setVisibility(View.GONE);
                        modificar_instructor.setVisibility(View.VISIBLE);

                        ci_instructor.setText(param.get("ci").toString());
                        ci_instructor.setEnabled(false);
                        nombre_instructor.setText(param.get("nombre").toString());
                        apellido_instructor.setText(param.get("apellido").toString());
                        if (param.get("sexo").toString().equals("F"))
                            femenino_instructor.setChecked(true);
                        else
                            masculino_instructor.setChecked(true);
                        telefono_instructor.setText(param.get("telefono").toString());
                        direccion_instructor.setText(param.get("direccion").toString());
                    }
                });
                eliminar_instructor.setOnClickListener(new View.OnClickListener() {
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
