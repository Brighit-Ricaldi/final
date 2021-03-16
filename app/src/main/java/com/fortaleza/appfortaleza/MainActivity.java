package com.fortaleza.appfortaleza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fortaleza.appfortaleza.Adapters.ListViewLocations;


public class MainActivity extends AppCompatActivity {
    private ImageView homemap;

    private ImageView addubicacionmap;

    private ImageView listar;

    private ImageView salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homemap = findViewById(R.id.home_map);
        addubicacionmap = findViewById(R.id.addubicacion_map);
        listar = findViewById(R.id.listar_datos);
        salir = findViewById(R.id.salir);

        homemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoMaps();

            }
        });

        addubicacionmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoAddubicacion();

            }
        });

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoListar();

            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salir();

            }
        });
    }

    public void GoMaps(){
        Intent intent = new Intent( MainActivity.this, MapsActivity.class);
        startActivity(intent);

    }
    public void GoAddubicacion(){
        Intent intent = new Intent( MainActivity.this, RegisterLugar_Activity.class);
        startActivity(intent);

    }
    public void GoListar(){
        Intent intent = new Intent( MainActivity.this, MainActivity.class);
        startActivity(intent);

    }
    public void salir(){
        Intent intent = new Intent( MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }
}