package com.fortaleza.appfortaleza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fortaleza.appfortaleza.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URL;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_prinregister;
    private EditText fullname_input;
    private EditText name_input;
    private EditText email_input;
    private EditText phone_input;
    private EditText password_input;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname_input = findViewById(R.id.fullname_input);
        name_input = findViewById(R.id.name_input);
        email_input = findViewById(R.id.email_input);
        phone_input = findViewById(R.id.phone_input);
        password_input = findViewById(R.id.password_input);
        btn_prinregister = findViewById(R.id.btn_prinregister);
        btn_login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();

        //Referencia al nodo principal de Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere un momento").build();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoLogin();
            }
        });
        btn_prinregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();

            }
        });
    }

    private void GoLogin(){
        Intent intent = new Intent( RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void  RegisterUser(){
        final String fullname = fullname_input.getText().toString().trim();
        final String name = name_input.getText().toString().trim();
        final String email = email_input.getText().toString().trim();
        final String phone = phone_input.getText().toString().trim();
        final String password = password_input.getText().toString().trim();

        // verificar que las cajas no esten vacias
        if(!fullname.isEmpty() && !name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !password.isEmpty()){
            if(password.length() >6){
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mDialog.hide();
                        if(task.isSuccessful()){
                            String id = mAuth.getCurrentUser().getUid();
                            saveUser(id, fullname, name, email, phone);


                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
            else{
                Toast.makeText(this, "Minimo 6 caracteres", Toast.LENGTH_SHORT).show();

            }
        }
        else{
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveUser(String id, String fullname, String name, String email, String phone) {
        User user = new User();
        user.setFullname(fullname);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        mDatabase.child("Users").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


}