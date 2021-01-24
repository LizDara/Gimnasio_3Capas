package com.example.gimnasio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.example.gimnasio.CapaPresentacion.PAparato;
import com.example.gimnasio.CapaPresentacion.PClase;
import com.example.gimnasio.CapaPresentacion.PCliente;
import com.example.gimnasio.CapaPresentacion.PInstructor;
import com.example.gimnasio.CapaPresentacion.PNota;
import com.example.gimnasio.CapaPresentacion.PSala;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @OnClick(R.id.sala)
    public void onClickSala(View v) {
        Intent intent = new Intent(this, PSala.class);
        startActivity(intent);
    }

    @OnClick(R.id.cliente)
    public void onClickCliente(View v) {
        Intent intent = new Intent(this, PCliente.class);
        startActivity(intent);
    }

    @OnClick(R.id.instructor)
    public void onClickInstructor(View v) {
        Intent intent = new Intent(this, PInstructor.class);
        startActivity(intent);
    }

    @OnClick(R.id.aparato)
    public void onClickAparato(View v) {
        Intent intent = new Intent(this, PAparato.class);
        startActivity(intent);
    }

    @OnClick(R.id.clase)
    public void onClickClase(View v) {
        Intent intent = new Intent(this, PClase.class);
        startActivity(intent);
    }

    @OnClick(R.id.nota)
    public void onClickNota(View v) {
        Intent intent = new Intent(this, PNota.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("GIMNASIO");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
    }
}
