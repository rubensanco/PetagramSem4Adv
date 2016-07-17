package com.example.rsc.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rsc.myapplication.fragments.MascotaRVFragment;
import com.example.rsc.myapplication.adapters.PageAdapter;
import com.example.rsc.myapplication.fragments.PerfilMascotaFragment;
import com.example.rsc.myapplication.R;
import com.example.rsc.myapplication.restApiHeroku.EndpointsHeroku;
import com.example.rsc.myapplication.restApiHeroku.adapter.RestApiHerokuAdapter;
import com.example.rsc.myapplication.restApiHeroku.model.UsuarioResponse;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Bundle bundle = getIntent().getExtras();
        int tab = 0;
        if (bundle!=null) {
            tab = getIntent().getExtras().getInt("tab", 0);
        }
        if (validaCuenta()){
            setUpViewPager(tab);
        }

        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Proximamente fotos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private boolean validaCuenta() {
        SharedPreferences miCuentaPref = getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);
        String cuentaUsuario = miCuentaPref.getString("cuenta",null);
        if(cuentaUsuario == null){
            Log.v("Cuenta","null");
            Intent intent = new Intent(MainActivity.this,ConfigCuentaActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Primero es necesario guardar tu cuenta", Toast.LENGTH_LONG).show();
            finish();
            return false;
        }else{
            Log.v("Cuenta",cuentaUsuario);
            return true;
        }


    }

    private ArrayList<Fragment> agregarFragments(){
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new MascotaRVFragment());
        fragments.add(new PerfilMascotaFragment());
        return fragments;
    }

    private void setUpViewPager(int tab) {
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(getResources().getString(R.string.mascotas));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_mascotas);
        tabLayout.getTabAt(1).setText(getResources().getString(R.string.mimascotatitulo));
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_mimascota);
        tabLayout.getTabAt(tab).select();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mcontacto) {
            Intent intent = new Intent(MainActivity.this,ContactoActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.macercade) {
            Intent intent = new Intent(MainActivity.this,AcercaDeActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.favoritos) {
            Intent intent = new Intent(MainActivity.this,FavoritosActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.mconfigcuenta){
            Intent intent = new Intent(MainActivity.this,ConfigCuentaActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.mrecibirnotif){
            registroToken();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void registroToken() {

        SharedPreferences miCuentaPref = getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);
        String idDispo = miCuentaPref.getString("idDispositivo", null);
        String idUsuInstagram = miCuentaPref.getString("cuenta",null);

        if(idDispo == null){
            Log.v("Id Dispositivo","null");
            Log.v("Cuenta",idUsuInstagram);
            idDispo = FirebaseInstanceId.getInstance().getToken();
            Log.v("Id Dispositivo_reg", idDispo);
            enviaIdDispositivo(idDispo, idUsuInstagram);
        }else{
            Log.v("Id Dispositivo",idDispo);
            Toast.makeText(MainActivity.this, "Dispositivo ya registrado", Toast.LENGTH_LONG).show();
        }

    }

    public void enviaIdDispositivo(String idDispo, String idUsuInstagram) {

        RestApiHerokuAdapter restApiHerokuAdapter = new RestApiHerokuAdapter();
        EndpointsHeroku endpointsHeroku = restApiHerokuAdapter.conexionRestAPIHeroku();
        Call<UsuarioResponse> usuarioResponseCall = endpointsHeroku.registrarTokenID(idDispo, idUsuInstagram);

        usuarioResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                UsuarioResponse usuarioResponse = response.body();
                Log.d("response(HEROKU)", usuarioResponse.toString());
                Log.d("ID(HEROKU)", usuarioResponse.getId());
                Log.d("ID_DISPO(HEROKU)", usuarioResponse.getIdDispositivo());
                Log.d("ID_USUARIO(HEROKU)", usuarioResponse.getIdUsuario());
                SharedPreferences miCuenta = getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = miCuenta.edit();
                editor.putString("idDispositivo", usuarioResponse.getId());
                editor.commit();
                Toast.makeText(MainActivity.this, "Registro dispositivo exitoso", Toast.LENGTH_LONG).show();
            }


            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                SharedPreferences miCuenta = getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = miCuenta.edit();
                editor.putString("idDispositivo",null);
                editor.commit();
                Toast.makeText(MainActivity.this, "Error en registro dispositivo", Toast.LENGTH_LONG).show();
            }
        });


    }
}
