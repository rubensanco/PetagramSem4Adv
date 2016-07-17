package com.example.rsc.myapplication.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.rsc.myapplication.R;
import com.example.rsc.myapplication.adapters.MascotaAdapter;
import com.example.rsc.myapplication.adapters.MascotaAdapterFav;
import com.example.rsc.myapplication.pojo.Mascota;
import com.example.rsc.myapplication.presenters.IPerfilUsuarioActivityPresenter;
import com.example.rsc.myapplication.presenters.PerfilUsuarioActivityPresenter;

import java.util.ArrayList;

public class PerfilUsuarioActivity extends AppCompatActivity implements IPerfilUsuarioActivity{

    private RecyclerView recycler;
    private IPerfilUsuarioActivityPresenter presenter;
    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("usuario");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);
        presenter = new PerfilUsuarioActivityPresenter(this,getApplicationContext(),usuario);
    }

    @Override
    public void generarLLVertical() {
        LinearLayoutManager lManager = new LinearLayoutManager(this);
        lManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(lManager);
    }

    @Override
    public MascotaAdapter crearAdaptador(ArrayList<Mascota> mascotas) {
        MascotaAdapter adapter = new MascotaAdapter(mascotas, this);
        return adapter;
    }

    @Override
    public void inicMascotaAdapter(MascotaAdapter mascotaAdapter) {
        recycler.setAdapter(mascotaAdapter);
    }
}
