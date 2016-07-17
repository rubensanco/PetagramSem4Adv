package com.example.rsc.myapplication.presenters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.rsc.myapplication.activities.IPerfilUsuarioActivity;
import com.example.rsc.myapplication.db.ConstructorMascotas;
import com.example.rsc.myapplication.pojo.Mascota;
import com.example.rsc.myapplication.restApi.EndpointsApi;
import com.example.rsc.myapplication.restApi.adapter.RestApiAdapter;
import com.example.rsc.myapplication.restApi.model.MascotaResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilUsuarioActivityPresenter implements IPerfilUsuarioActivityPresenter {

    private IPerfilUsuarioActivity iPerfilUsuarioActivity;
    private Context context;
    private ConstructorMascotas constructorMascotas;
    private ArrayList<Mascota> topMascotas;
    private ProgressDialog dialog;
    private String usuario;

    public PerfilUsuarioActivityPresenter(IPerfilUsuarioActivity iPerfilUsuarioActivity, Context context,String usuario) {
        this.iPerfilUsuarioActivity = iPerfilUsuarioActivity;
        this.context = context;
        this.usuario = usuario;

        buscaIDUsuario(usuario);
    }


    @Override
    public void buscaIDUsuario(String usuario) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonSearchUser = restApiAdapter.gsonDeserialUsersFollows();
        EndpointsApi endpointsApi = restApiAdapter.conexionRestApi(gsonSearchUser);
        Call<MascotaResponse> mascotaResponseCall = endpointsApi.getUserSearch(usuario);
        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                Log.v("CODE_SEARCH", String.valueOf(response.code()));
                MascotaResponse mascotaResponse = response.body();
                if (response.code() == 200) {
                    String userID = mascotaResponse.getMascotas().get(0).getId();
                    obtenerRecientesAPI(userID);
                } else {
                    Toast.makeText(context, "Lo sentimos no se pudo realizar follow/unfollow", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Log.v("CODE", "-1000");
                Toast.makeText(context, "Problemas de conexión. Intenta nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void obtenerRecientesAPI(final String userID) {

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonRecentMedia = restApiAdapter.gsonDeserialRecentMedia();
        EndpointsApi endpointsApiRecent = restApiAdapter.conexionRestApi(gsonRecentMedia);
        Call<MascotaResponse> mascotaResponseCall = endpointsApiRecent.getUserRecentMedia(userID, 3);
        Log.v("ID", userID);
        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                if (response.code() == 200) {
                    Log.v("CODE recientes", String.valueOf(response.code()));
                    Log.v("OK recientes", userID);
                    topMascotas = mascotaResponse.getMascotas();
                    mostrarMascotasRV();
                    Log.v("OK", "OK");
                } else {
                    Log.v("MAL", userID);
                }

            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "Problemas de conexión. Intenta nuevamente", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void mostrarMascotasRV() {
        iPerfilUsuarioActivity.inicMascotaAdapter(iPerfilUsuarioActivity.crearAdaptador(topMascotas));
        iPerfilUsuarioActivity.generarLLVertical();
    }


}
