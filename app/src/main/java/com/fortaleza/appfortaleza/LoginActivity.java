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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private EditText email_input;
    private EditText password_input;
    private Button btn_prinlogin;
    private Button btn_register;

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;



    AlertDialog mDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
        btn_register = findViewById(R.id.btn_register);
        btn_prinlogin = findViewById(R.id.btn_prinlogin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Espere un momento").build();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoRegister();
            }
        });

        btn_prinlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callLogin();

            }
        });


    }

    public void GoRegister(){
        Intent intent = new Intent( LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void callLogin(){
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()){
            if(password.length() >6){
                mDialog.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Inicio de Sesión con exito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent( LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "El email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                        mDialog.dismiss();
                    }
                });

            }
            else{
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "El email y contraseña son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
}