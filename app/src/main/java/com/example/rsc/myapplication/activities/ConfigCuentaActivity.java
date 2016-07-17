package com.example.rsc.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rsc.myapplication.R;

public class ConfigCuentaActivity extends AppCompatActivity {

    private EditText etCuenta;
    private Button btnGuardarCuenta;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_cuenta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        etCuenta = (EditText) findViewById(R.id.etCuenta);
        btnGuardarCuenta = (Button) findViewById(R.id.btnGuardarCuenta);
        btnGuardarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardacuenta(v);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(ConfigCuentaActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            //onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void guardacuenta(View v) {

        String cuentaUsuario = etCuenta.getText().toString().trim();
        SharedPreferences miCuentaPref = getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miCuentaPref.edit();
        editor.putString("cuenta", cuentaUsuario);
        if(editor.commit()) {
            Snackbar.make(v, "Cuenta guardada exitosamente", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Intent intent = new Intent(ConfigCuentaActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Snackbar.make(v, "No se pudo guardar tu Cuenta", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

}
