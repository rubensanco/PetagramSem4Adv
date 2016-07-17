package com.example.rsc.myapplication.restApi.model;

import com.example.rsc.myapplication.pojo.Mascota;

import java.util.ArrayList;


public class MascotaResponse {
    ArrayList<Mascota> mascotas;

    public ArrayList<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(ArrayList<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
}
