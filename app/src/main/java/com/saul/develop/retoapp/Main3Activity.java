package com.saul.develop.retoapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.lang.reflect.Array;

import static com.saul.develop.retoapp.R.id.nav_estadisticos;

public class Main3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String user;
    public static String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // seteamos un fragment
        Estadisticos fragment = new Estadisticos();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        // sacamos las llaves guardadas en el activity anterior
        // Restore preferences
        SharedPreferences llaves = getSharedPreferences(Login.USER_SESSION, 0);
        user = llaves.getString("usuario",null);
        pass = llaves.getString("pass",null);
        Log.v("ActivityTag","Auser: "+user);
        Log.v("ActivityTag","Apass: "+pass);


        // intentamos pasarle al fragment las variables
        fragment.setUser(user,pass);






    } //onCreate

    //Metodo para devolver el usuario
    public static String[] getUser(){
        //declaro mis datos
        String[] response = {
                user,
                pass
        };
        return response;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == nav_estadisticos) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Estadisticos()).commit();
        } else if (id == R.id.nav_aspirante) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Aspirantes()).commit();
        } else if (id == R.id.nav_becarios) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Becarios()).commit();
        } else if (id == R.id.nav_empresarios) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Empresarios()).commit();
        } else if (id == R.id.nav_contacto) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Contacto()).commit();
        } else if(id == R.id.perfil){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Perfil()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void saul(View view){
        Intent intent = new Intent(this, Detalle.class);
        startActivity(intent);
    }

}