package com.example.rsc.myapplication.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.example.rsc.myapplication.db.ConstructorMascotas;
import com.example.rsc.myapplication.db.ConstructorMiMascota;
import com.example.rsc.myapplication.fragments.IPerfilMascotaFragment;
import com.example.rsc.myapplication.pojo.Mascota;
import com.example.rsc.myapplication.restApi.EndpointsApi;
import com.example.rsc.myapplication.restApi.adapter.RestApiAdapter;
import com.example.rsc.myapplication.restApi.model.MascotaResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PerfilMascotaFragmentPresenter implements IPerfilMascotaFragmentPresenter {

    private IPerfilMascotaFragment iPerfilMascotaFragment;
    private Context context;
    private ConstructorMiMascota constructorMiMascota;
    private ArrayList<Mascota> miMascota;
    private String usuario = "";
    private Mascota mascotaPerfil;


    public PerfilMascotaFragmentPresenter(IPerfilMascotaFragment iPerfilMascotaFragment, Context context) {
        this.iPerfilMascotaFragment = iPerfilMascotaFragment;
        this.context = context;
//        obtenerDatosMiMascota();
        obtenerSharedPreferences();
        obtenerSearchAPI(usuario);
    }


    @Override
    public void obtenerDatosMiMascota() {
        constructorMiMascota = new ConstructorMiMascota(context);
        miMascota = constructorMiMascota.obtenerMiMascota();
        mostrarDatosMiMascota();
    }

    @Override
    public void mostrarDatosMiMascota() {
        iPerfilMascotaFragment.inicMiMascotaAdapter(iPerfilMascotaFragment.crearAdaptadorMiMascota(miMascota));
        iPerfilMascotaFragment.generarGridLayout();
    }

    @Override
    public void obtenerRecientesAPI(final String userId) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonRecentMedia = restApiAdapter.gsonDeserialRecentMedia();
        EndpointsApi endpointsApiRecent = restApiAdapter.conexionRestApi(gsonRecentMedia);
        Call<MascotaResponse> mascotaResponseCall = endpointsApiRecent.getUserRecentMediaAll(userId);
        Log.v("ID", userId);
        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                if(response.code() == 200){
                    Log.v("CODE", String.valueOf(response.code()));
                    Log.v("OK", userId);
                    miMascota = mascotaResponse.getMascotas();
                    mostrarDatosMiMascota();
                }else{
                    Log.v("MAL", userId);
                    Toast.makeText(context, "Usuario sin permiso", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "Problemas de conexión. Intenta nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void obtenerSearchAPI(String q) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonSearchUsers = restApiAdapter.gsonDeserialUsersFollows();
        EndpointsApi endpointsApiUser = restApiAdapter.conexionRestApi(gsonSearchUsers);
        Call<MascotaResponse> mascotaResponseSearchUser = endpointsApiUser.getUserSearch(q);
        mascotaResponseSearchUser.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                if (mascotaResponse.getMascotas().size()>0) {
                    String id = mascotaResponse.getMascotas().get(0).getId();
                    String nombre = mascotaResponse.getMascotas().get(0).getNombre();
                    String profileImagen = mascotaResponse.getMascotas().get(0).getImagen();
                    mascotaPerfil = new Mascota(id, nombre, profileImagen, 0);
                    iPerfilMascotaFragment.datosMiMascota(mascotaPerfil);

                    obtenerRecientesAPI(id);
                }
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "Problemas de conexión. Intenta nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtenerSharedPreferences(){
        SharedPreferences miCuentaPref = context.getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);
        usuario = miCuentaPref.getString("cuenta",null);
    }
}
