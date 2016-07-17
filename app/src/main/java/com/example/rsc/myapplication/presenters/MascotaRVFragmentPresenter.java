package com.example.rsc.myapplication.presenters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rsc.myapplication.db.ConstructorMascotas;
import com.example.rsc.myapplication.fragments.IMascotaRVFragment;
import com.example.rsc.myapplication.pojo.Mascota;
import com.example.rsc.myapplication.restApi.ConstantesRestApi;
import com.example.rsc.myapplication.restApi.EndpointsApi;
import com.example.rsc.myapplication.restApi.adapter.RestApiAdapter;
import com.example.rsc.myapplication.restApi.model.MascotaResponse;
import com.example.rsc.myapplication.restApi.model.MediaLikeResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MascotaRVFragmentPresenter implements IMascotaRVFragmentPresenter{

    private IMascotaRVFragment iMascotaRVFragment;
    private Context context;
    private ConstructorMascotas constructorMascotas;
    private ArrayList<Mascota> mascotas = new ArrayList<Mascota>();
    private ArrayList<Mascota> vector;
    private ArrayList<Mascota> usuarios;
    private View view;
    private ProgressDialog dialog;

    public MascotaRVFragmentPresenter(IMascotaRVFragment iMascotaRVFragment, Context context) {
        this.iMascotaRVFragment = iMascotaRVFragment;
        this.context = context;
        //obtenerMascotasBD();

        obtenerUsuariosAPI();
    }

    @Override
    public void obtenerMascotasBD() {
        constructorMascotas = new ConstructorMascotas(context);
        mascotas = constructorMascotas.obtenerMascotas();
        mostrarMascotasRV();
    }

    @Override
    public void mostrarMascotasRV() {
        iMascotaRVFragment.inicMascotaAdapter(iMascotaRVFragment.crearAdaptador(mascotas));
        iMascotaRVFragment.generarLLVertical();
    }

    @Override
    public void registrarLikeBtn() {

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
                    if(response.code() == 200){
                        Log.v("CODE recientes", String.valueOf(response.code()));
                        Log.v("OK recientes", userID);
                        vector = mascotaResponse.getMascotas();
                        mascotas.addAll(vector);
                        Log.v("OK", "OK");
                    }else{
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
    public void obtenerUsuariosAPI() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonUsersFollows = restApiAdapter.gsonDeserialUsersFollows();
        EndpointsApi endpointsApiUser = restApiAdapter.conexionRestApi(gsonUsersFollows);
        Call<MascotaResponse> mascotaResponseCallUser = endpointsApiUser.getUserFollows();
        mascotaResponseCallUser.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                Log.v("CODE", String.valueOf(response.code()));
                MascotaResponse mascotaResponse = response.body();
                usuarios = mascotaResponse.getMascotas();
                for (int i = 0; i < usuarios.size() ; i++) {
                    obtenerRecientesAPI(usuarios.get(i).getId());

                }
                obtenerRecientesAPI("3437117661");
                try {

                    dialog = new ProgressDialog(context);
                    dialog.setTitle("  Cargando.... ");
                    dialog.setCancelable(false);
                    dialog.show();

                    Runnable progressRunnable = new Runnable() {

                        @Override
                        public void run() {
                            dialog.cancel();
                            mostrarMascotasRV();
                        }

                    };

                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 10000);

                } catch (Exception e) {
                    Log.v("SLEEP","SLEEP");
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
    public void registrarLikeAPI(String idFotoInstagram) {

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaLike = restApiAdapter.gsonDeserialSetLikeMedia();
        EndpointsApi endpointsApi = restApiAdapter.conexionRestApi(gsonMediaLike);
        Call<MediaLikeResponse> mediaLikeResponseCall = endpointsApi.setLikeMedia(idFotoInstagram,ConstantesRestApi.ACCESS_TOKEN);
        mediaLikeResponseCall.enqueue(new Callback<MediaLikeResponse>() {
            @Override
            public void onResponse(Call<MediaLikeResponse> call, Response<MediaLikeResponse> response) {
                MediaLikeResponse mediaLikeResponse = response.body();
                if(mediaLikeResponse.getCode() == 200){
                    Toast.makeText(context, "Diste Like", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Error intenta mas tarde", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MediaLikeResponse> call, Throwable t) {

            }
        });
    }
}
