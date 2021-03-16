package com.fortaleza.appfortaleza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fortaleza.appfortaleza.model.Location;
import com.fortaleza.appfortaleza.model.Marcador;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterLugar_Activity extends AppCompatActivity implements View.OnClickListener{


    private List<Marcador> listaCoordenada = new ArrayList<  >();
    ArrayAdapter<Location> arrayAdapterCoordenadas;

    EditText latitud_input,longitud_input,rasocial_input,nomyape_input,
            email_input,phone_input, celular_input, manager_input,ruc_input;
    ListView listaView_coordenada;

    Toolbar mToolbar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_lugar);


        longitud_input = findViewById(R.id.longitud_input);
        latitud_input = findViewById(R.id.latitud_input);
        rasocial_input = findViewById(R.id.rasocial_input);
        nomyape_input = findViewById(R.id.nomyape_input);
        email_input = findViewById(R.id.email_input);
        phone_input = findViewById(R.id.phone_input);
        manager_input = findViewById(R.id.manager_input);
        ruc_input = findViewById(R.id.ruc_input);
        celular_input = findViewById(R.id.celular_input);

        listaView_coordenada = findViewById(R.id.lv_datosCoordenadas);
        iniciarFirebase();

        Button bGuardar = (Button) findViewById(R.id.btnGuardar_registro);
        bGuardar.setOnClickListener(this);

        // ver toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Registra");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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
        celular_input.setText("");
        manager_input.setText("");
        ruc_input.setText("");
    }

    private void validacion() {
        Double latitudP = Double.parseDouble(latitud_input.getText().toString());
        Double longitudP = Double.parseDouble(longitud_input.getText().toString());
        String rasocial_inputP = rasocial_input.getText().toString();
        String nomyape_inputP = nomyape_input.getText().toString();
        String email_inputP = email_input.getText().toString();
        String phone_inputP = phone_input.getText().toString();
        String celular_inputP = celular_input.getText().toString();
        String manager_inputP = manager_input.getText().toString();
        String ruc_inputP = ruc_input.getText().toString();

        if (latitudP.equals("")){
            latitud_input.setError("Required");
        }else if (longitudP.equals("")){
            latitud_input.setError("Required");
        }
    }

    @Override
    public void onClick(View v) {
        Double latitudP = Double.parseDouble(latitud_input.getText().toString());
        Double longitudP = Double.parseDouble(longitud_input.getText().toString());
        String rasocial_inputP = rasocial_input.getText().toString();
        String nomyape_inputP = nomyape_input.getText().toString();
        String email_inputP = email_input.getText().toString();
        String phone_inputP = phone_input.getText().toString();
        String celular_inputP = celular_input.getText().toString();
        String manager_inputP = manager_input.getText().toString();
        String ruc_inputP = ruc_input.getText().toString();

        switch (v.getId()) {
            case R.id.btnGuardar_registro: {
                if (latitudP.equals("") || longitudP.equals("") ){
                    validacion();
                }else {
                    Location u = new Location();
                    u.setLatitud(latitudP);
                    u.setLongitud(longitudP);
                    u.setRazon_social(rasocial_inputP);
                    u.setNombre_apellidos(nomyape_inputP);
                    u.setEmail(email_inputP);
                    u.setTelefono(phone_inputP);
                    u.setCelular(celular_inputP);
                    u.setRepresentante_cargo(manager_inputP);
                    u.setRuc(ruc_inputP);
                    Map<String, Object> latlong = new HashMap<>();
                    latlong.put("latitud",u.getLatitud());
                    latlong.put("longitud",u.getLongitud());
                    latlong.put("razon_social",u.getRazon_social());
                    latlong.put("telefono",u.getTelefono());
                    latlong.put("celular", u.getCelular());
                    latlong.put("email",u.getEmail());
                    latlong.put("nombre_apellidos",u.getNombre_apellidos());
                    latlong.put("representante_cargo",u.getRepresentante_cargo());
                    latlong.put("ruc",u.getRuc());
                    databaseReference.child("locations").push().setValue(latlong);
                    Toast.makeText(this,"Coordenadas Agregadas", Toast.LENGTH_LONG).show();
                    limpiar();
                }
                break;
            }
        }

    }

}