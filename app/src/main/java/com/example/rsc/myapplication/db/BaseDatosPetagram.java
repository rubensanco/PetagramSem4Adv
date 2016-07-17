package com.example.rsc.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rsc.myapplication.R;
import com.example.rsc.myapplication.pojo.Mascota;

import java.util.ArrayList;


public class BaseDatosPetagram extends SQLiteOpenHelper {

    private Context context;

    public BaseDatosPetagram(Context context) {
        super(context, ConstantesBDPetagram.DATABASE_NAME, null, ConstantesBDPetagram.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCrearTablaMascota = "CREATE TABLE " + ConstantesBDPetagram.TABLE_MASCOTA + "(" +
                ConstantesBDPetagram.TABLE_MASCOTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBDPetagram.TABLE_MASCOTA_NOMBRE + " TEXT, " +
                ConstantesBDPetagram.TABLE_MASCOTA_FOTO + " TEXT " +
                ")";

        String queryCrearTablaMascotaLikes = "CREATE TABLE " + ConstantesBDPetagram.TABLE_LIKES_MASCOTA + "(" +
                ConstantesBDPetagram.TABLE_LIKES_MASCOTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBDPetagram.TABLE_LIKES_MASCOTA_ID_MASCOTA + " INTEGER, " +
                ConstantesBDPetagram.TABLE_LIKES_MASCOTA_NUMERO_LIKES + " INTEGER, " +
                "FOREIGN KEY (" + ConstantesBDPetagram.TABLE_LIKES_MASCOTA_ID_MASCOTA + ") " +
                "REFERENCES " + ConstantesBDPetagram.TABLE_MASCOTA + "("+ ConstantesBDPetagram.TABLE_MASCOTA_ID +")" +
                ")";

        db.execSQL(queryCrearTablaMascota);
        db.execSQL(queryCrearTablaMascotaLikes);
        insertarTodasLasMascotas(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + ConstantesBDPetagram.TABLE_MASCOTA);
        db.execSQL("DROP TABLE IF EXIST " + ConstantesBDPetagram.TABLE_LIKES_MASCOTA);
        onCreate(db);
    }

    public void insertarTodasLasMascotas (SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_NOMBRE, "Rocky");
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_FOTO, R.drawable.perro_02);
        db.insert(ConstantesBDPetagram.TABLE_MASCOTA, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_NOMBRE, "Aquiles");
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_FOTO, R.drawable.perro_01);
        db.insert(ConstantesBDPetagram.TABLE_MASCOTA, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_NOMBRE, "Mike");
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_FOTO, R.drawable.perro_03);
        db.insert(ConstantesBDPetagram.TABLE_MASCOTA, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_NOMBRE, "Mateo");
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_FOTO, R.drawable.perro_04);
        db.insert(ConstantesBDPetagram.TABLE_MASCOTA, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_NOMBRE,"Bolillo");
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_FOTO, R.drawable.perro_05);
        db.insert(ConstantesBDPetagram.TABLE_MASCOTA, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_NOMBRE,"Fido");
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_FOTO, R.drawable.perro_06);
        db.insert(ConstantesBDPetagram.TABLE_MASCOTA, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_NOMBRE,"Lucas");
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_FOTO, R.drawable.perro_07);
        db.insert(ConstantesBDPetagram.TABLE_MASCOTA, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_NOMBRE,"Furia");
        contentValues.put(ConstantesBDPetagram.TABLE_MASCOTA_FOTO, R.drawable.perro_08);
        db.insert(ConstantesBDPetagram.TABLE_MASCOTA, null, contentValues);

    }

    public ArrayList<Mascota> obtenerMascotas(){
        ArrayList<Mascota> mascotas = new ArrayList<Mascota>();

        String query = "SELECT * FROM " + ConstantesBDPetagram.TABLE_MASCOTA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while(registros.moveToNext()){
            Mascota mascotaActual = new Mascota();
            /*mascotaActual.setId(registros.getInt(0));
            mascotaActual.setNombre(registros.getString(1));
            mascotaActual.setImagen(registros.getInt(2));*/

            String queryLikes = "SELECT COUNT("+ ConstantesBDPetagram.TABLE_LIKES_MASCOTA_NUMERO_LIKES +") as likes" +
                    " FROM " + ConstantesBDPetagram.TABLE_LIKES_MASCOTA +
                    " WHERE " + ConstantesBDPetagram.TABLE_LIKES_MASCOTA_ID_MASCOTA + " = " +
                    mascotaActual.getId();
            Cursor registrosLikes = db.rawQuery(queryLikes,null);

            if (registrosLikes.moveToNext()) {

                mascotaActual.setLikes(registrosLikes.getInt(0));
            }else{
                mascotaActual.setLikes(0);
            }

            mascotas.add(mascotaActual);
        }
        db.close();

        return mascotas;
    }

    public ArrayList<Mascota> obtenerTopMascotas(){
        ArrayList<Mascota> mascotasTop = new ArrayList<Mascota>();

        String queryLikes = "SELECT " + ConstantesBDPetagram.TABLE_LIKES_MASCOTA_ID_MASCOTA + ", " +
                            "COUNT("+ ConstantesBDPetagram.TABLE_LIKES_MASCOTA_NUMERO_LIKES +") as likes" +
                            " FROM " + ConstantesBDPetagram.TABLE_LIKES_MASCOTA +
                            " GROUP BY " + ConstantesBDPetagram.TABLE_LIKES_MASCOTA_ID_MASCOTA +
                            " ORDER BY 2 DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registrosLikes = db.rawQuery(queryLikes,null);
        int i = 1;

        while (registrosLikes.moveToNext() && i<=5){
            Mascota mascotaActual = new Mascota();
//            mascotaActual.setId(registrosLikes.getInt(0));
            mascotaActual.setLikes(registrosLikes.getInt(1));

            String query = "SELECT * FROM " + ConstantesBDPetagram.TABLE_MASCOTA +
                        " WHERE " + ConstantesBDPetagram.TABLE_MASCOTA_ID + " = " + mascotaActual.getId();

            Cursor registros = db.rawQuery(query, null);
            if (registros.moveToNext()) {
                mascotaActual.setNombre(registros.getString(1));
//                mascotaActual.setImagen(registros.getInt(2));
            }
            i++;
            mascotasTop.add(mascotaActual);
        }
        db.close();
        return mascotasTop;
    }

    public void insertarMascota (ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBDPetagram.TABLE_MASCOTA, null, contentValues);
        db.close();
    }

    public void insertarLikeMascota(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBDPetagram.TABLE_LIKES_MASCOTA,null,contentValues);
        db.close();
    }

    public int obtenerLikesMascota(Mascota mascota){
        int likes = 0;
        String queryLikes = "SELECT COUNT("+ ConstantesBDPetagram.TABLE_LIKES_MASCOTA_NUMERO_LIKES +") as likes" +
                " FROM " + ConstantesBDPetagram.TABLE_LIKES_MASCOTA +
                " WHERE " + ConstantesBDPetagram.TABLE_LIKES_MASCOTA_ID_MASCOTA + " = " +
                mascota.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registrosLikes = db.rawQuery(queryLikes,null);

        if (registrosLikes.moveToNext()) {
            likes = registrosLikes.getInt(0);
        }
        db.close();
        return likes;
    }


}
