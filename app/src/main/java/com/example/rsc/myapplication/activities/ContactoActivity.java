package com.example.rsc.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rsc.myapplication.tasks.EnviaCorreo;
import com.example.rsc.myapplication.R;

public class ContactoActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etCorreo;
    private EditText etAsunto;
    private EditText etMensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etAsunto = (EditText) findViewById(R.id.etAsunto);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        Button btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar();
            }
        });


    }

    public void enviar(){
        String correo = etCorreo.getText().toString().trim();
        String asunto = etAsunto.getText().toString().trim();
        String mensaje = etMensaje.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();

        //Creating SendMail object
        EnviaCorreo sm = new EnviaCorreo(this, nombre,correo, asunto, mensaje);

        //Executing sendmail to send email
        sm.execute();

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
