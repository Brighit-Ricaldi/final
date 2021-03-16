package com.fortaleza.appfortaleza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.fortaleza.appfortaleza.model.Marcador;
import com.fortaleza.appfortaleza.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListViewLocations extends AppCompatActivity {
    RecyclerView recyclerView;


    private List<Marcador> listaCoordenada = new ArrayList<Marcador>();
    ArrayAdapter<Marcador> arrayAdapterCoordenadas;



    EditText latitud_input,longitud_input,rasocial_input,nomyape_input,email_input,phone_input,manager_input,ruc_input;
    ListView listaView_coordenada;
    Toolbar mToolbar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_locations);

        longitud_input = findViewById(R.id.longitud_input);
        latitud_input = findViewById(R.id.latitud_input);
        rasocial_input = findViewById(R.id.rasocial_input);
        nomyape_input = findViewById(R.id.nomyape_input);
        email_input = findViewById(R.id.email_input);
        phone_input = findViewById(R.id.phone_input);
        manager_input = findViewById(R.id.manager_input);
        ruc_input = findViewById(R.id.ruc_input);

        // ver toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Lista");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listaView_coordenada = findViewById(R.id.lv_datosCoordenadas);
        iniciarFirebase();
        listarDatos();


    }

    private void iniciarFirebase(){
        //propiedad de firebase ponga inicio de la App
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void limpiar() {
        latitud_input.setText("");
        longitud_input.setText("");
        rasocial_input.setText("");
        nomyape_input.setText("");
        email_input.setText("");
        phone_input.setText("");
        manager_input.setText("");
        ruc_input.setText("");
    }

    private void listarDatos(){
        databaseReference.child("locations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaCoordenada.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Marcador u = objSnapshot.getValue(Marcador.class);

                    listaCoordenada.add(u);
                    arrayAdapterCoordenadas=new ArrayAdapter<Marcador>(com.fortaleza.appfortaleza.ListViewLocations.this,
                            android.R.layout.simple_list_item_1,listaCoordenada);
                    listaView_coordenada.setAdapter(arrayAdapterCoordenadas);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}