package com.example.rsc.myapplication.activities;

import com.example.rsc.myapplication.adapters.MascotaAdapter;
import com.example.rsc.myapplication.pojo.Mascota;

import java.util.ArrayList;


public interface IPerfilUsuarioActivity {
    public void generarLLVertical();

    public MascotaAdapter crearAdaptador (ArrayList<Mascota> mascotas);

    public void inicMascotaAdapter (MascotaAdapter mascotaAdapter);
}
