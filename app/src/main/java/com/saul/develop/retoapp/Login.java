package com.saul.develop.retoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    public static final String USER_SESSION = "user";

    Button siguiente;
    Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        siguiente = (Button) findViewById(R.id.logIn);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checaUsuario();

            }
        });

        registro = (Button) findViewById(R.id.registro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(Login.this, Registro.class);
                startActivity(registro);
            }
        });
    } //on Create

    public void checaUsuario(){

        EditText usuario = (EditText) findViewById(R.id.elUsuario);
        String elUsuario = usuario.getText().toString();
        EditText pass = (EditText) findViewById(R.id.elPass);
        String elPass = pass.getText().toString();


        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(USER_SESSION, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("usuario", elUsuario);
        editor.putString("pass", elPass);

        // Commit the edits!
        editor.commit();

        Intent intent = new Intent(Login.this, Main3Activity.class);
        startActivity(intent);

    }
}
