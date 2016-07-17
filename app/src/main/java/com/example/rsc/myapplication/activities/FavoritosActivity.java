package com.example.rsc.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rsc.myapplication.adapters.MascotaAdapter;
import com.example.rsc.myapplication.pojo.Mascota;
import com.example.rsc.myapplication.adapters.MascotaAdapterFav;
import com.example.rsc.myapplication.R;
import com.example.rsc.myapplication.presenters.FavoritosActivityPresenter;
import com.example.rsc.myapplication.presenters.IFavoritosActivityPresenter;

import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity implements IFavoritosActivity{

    private RecyclerView recycler;
    private IFavoritosActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);
        presenter = new FavoritosActivityPresenter(this,getApplicationContext());






    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void generarLLVertical() {
        LinearLayoutManager lManager = new LinearLayoutManager(this);
        lManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(lManager);
    }

    @Override
    public MascotaAdapterFav crearAdaptador(ArrayList<Mascota> mascotas) {

        MascotaAdapterFav adapter = new MascotaAdapterFav(mascotas);
        if(adapter.getItemCount()==0){
            Toast.makeText(FavoritosActivity.this, "Ninguna mascota tiene likes", Toast.LENGTH_SHORT).show();
        }
        return adapter;
    }

    @Override
    public void inicMascotaAdapter(MascotaAdapterFav mascotaAdapter) {
        recycler.setAdapter(mascotaAdapter);
    }
}
