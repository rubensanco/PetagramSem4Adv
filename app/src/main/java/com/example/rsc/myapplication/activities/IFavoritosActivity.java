package com.example.rsc.myapplication.activities;

import com.example.rsc.myapplication.adapters.MascotaAdapterFav;
import com.example.rsc.myapplication.pojo.Mascota;

import java.util.ArrayList;


public interface IFavoritosActivity {

    public void generarLLVertical();

    public MascotaAdapterFav crearAdaptador (ArrayList<Mascota> mascotas);

    public void inicMascotaAdapter (MascotaAdapterFav mascotaAdapter);

}
