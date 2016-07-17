package com.example.rsc.myapplication.db;

import android.content.Context;

import com.example.rsc.myapplication.R;
import com.example.rsc.myapplication.pojo.Mascota;

import java.util.ArrayList;


public class ConstructorMiMascota {

    private Context context;

    public ConstructorMiMascota(Context context) {
        this.context = context;
    }

    public ArrayList<Mascota> obtenerMiMascota (){
        ArrayList<Mascota> miMascota = new ArrayList<Mascota>();
       /* miMascota.add(new Mascota("Rocky",R.drawable.perfil_01,7));
        miMascota.add(new Mascota("Rocky",R.drawable.perfil_02,4));
        miMascota.add(new Mascota("Rocky",R.drawable.perfil_03,3));
        miMascota.add(new Mascota("Rocky",R.drawable.perfil_04,8));
        miMascota.add(new Mascota("Rocky",R.drawable.perfil_05,0));
        miMascota.add(new Mascota("Rocky",R.drawable.perfil_06,1));
        miMascota.add(new Mascota("Rocky",R.drawable.perfil_07,9));*/

        return miMascota;
    }
}
