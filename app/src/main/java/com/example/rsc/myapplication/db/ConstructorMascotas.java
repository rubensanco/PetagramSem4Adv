package com.example.rsc.myapplication.db;

import android.content.ContentValues;
import android.content.Context;

import com.example.rsc.myapplication.R;
import com.example.rsc.myapplication.pojo.Mascota;

import java.util.ArrayList;


public class ConstructorMascotas {

    private static final int LIKE = 1;
    private Context context;

    public ConstructorMascotas(Context context) {
        this.context = context;
    }

    public ArrayList<Mascota> obtenerMascotas(){
        BaseDatosPetagram db = new BaseDatosPetagram(context);

        return db.obtenerMascotas();
    }

    public void darLikeMascota (Mascota mascota){
        BaseDatosPetagram db = new BaseDatosPetagram(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBDPetagram.TABLE_LIKES_MASCOTA_ID_MASCOTA, mascota.getId());
        contentValues.put(ConstantesBDPetagram.TABLE_LIKES_MASCOTA_NUMERO_LIKES, LIKE);
        db.insertarLikeMascota(contentValues);
    }

    public int obtenerLikesMascota(Mascota mascota){
        BaseDatosPetagram db = new BaseDatosPetagram(context);
        return db.obtenerLikesMascota(mascota);
    }

    public ArrayList<Mascota> obtenerTopMascotas(){
       BaseDatosPetagram db = new BaseDatosPetagram(context);
       return db.obtenerTopMascotas();
    }
}
