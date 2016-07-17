package com.example.rsc.myapplication.fragments;


import com.example.rsc.myapplication.adapters.MiMascotaAdapter;
import com.example.rsc.myapplication.pojo.Mascota;

import java.util.ArrayList;


public interface IPerfilMascotaFragment {

    public void generarGridLayout();

    public MiMascotaAdapter crearAdaptadorMiMascota (ArrayList<Mascota> mascotas);

    public void inicMiMascotaAdapter (MiMascotaAdapter miMascotaAdapter);

    public void datosMiMascota (Mascota mascotaPerfil);
}
