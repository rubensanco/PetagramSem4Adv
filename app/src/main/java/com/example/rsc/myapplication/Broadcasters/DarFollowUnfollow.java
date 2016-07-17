package com.example.rsc.myapplication.Broadcasters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.rsc.myapplication.restApi.EndpointsApi;
import com.example.rsc.myapplication.restApi.adapter.RestApiAdapter;
import com.example.rsc.myapplication.restApi.model.MascotaResponse;
import com.example.rsc.myapplication.restApi.model.RelationshipResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DarFollowUnfollow extends BroadcastReceiver {
    private static final String DA_FOLLOW = "follow";
    private static final String DA_UNFOLLOW = "unfollow";
    private static final String FOLLOWS = "follows";
    String usuario = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        String ACTION_KEY = "DAR_FOLLOW";
        String accion = intent.getAction();



        if (ACTION_KEY.equals(accion)){
            Bundle bundle = intent.getExtras();
            usuario = bundle.getString("usuario");
            buscaIDUsuario(usuario,context);
        }


    }

    private void buscaIDUsuario(final String usuario, final Context context) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonSearchUser = restApiAdapter.gsonDeserialUsersFollows();
        EndpointsApi endpointsApi = restApiAdapter.conexionRestApi(gsonSearchUser);
        Call<MascotaResponse> mascotaResponseCall = endpointsApi.getUserSearch(usuario);
        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                Log.v("CODE_SEARCH", String.valueOf(response.code()));
                MascotaResponse mascotaResponse = response.body();
                if(response.code()==200){
                    String userID = mascotaResponse.getMascotas().get(0).getId();
                    obtieneRelationship(userID,context);
                }else{
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

    private void obtieneRelationship(final String userID, final Context context) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonRelationship = restApiAdapter.gsonDeserialRelationship();
        EndpointsApi endpointsApi = restApiAdapter.conexionRestApi(gsonRelationship);
        Call<RelationshipResponse> relationshipResponseCall = endpointsApi.getRelationship(userID);
        relationshipResponseCall.enqueue(new Callback<RelationshipResponse>() {
            @Override
            public void onResponse(Call<RelationshipResponse> call, Response<RelationshipResponse> response) {
                Log.v("CODE_SEARCH", String.valueOf(response.code()));
                RelationshipResponse relationshipResponse = response.body();
                if(response.code()==200){
                    String obre = relationshipResponse.getOutgoing_status();
                    Log.d("OBRE",obre + " " + FOLLOWS);

                    if(obre.equals(FOLLOWS)){
                        Log.d("ACT", "Da unfollow");
                        realizaFollowUnfollow(userID,context,DA_UNFOLLOW);
                    }else{
                        Log.d("ACT","Da follow");
                        realizaFollowUnfollow(userID, context, DA_FOLLOW);
                    }

                }else{
                    Toast.makeText(context, "Lo sentimos no se pudo realizar follow/unfollow", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RelationshipResponse> call, Throwable t) {
                Log.v("CODE", "-2000");
                Toast.makeText(context, "Problemas de conexión. Intenta nuevamente", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void realizaFollowUnfollow(String userID, final Context context, String action) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonRelationship = restApiAdapter.gsonDeserialRelationship();
        EndpointsApi endpointsApi = restApiAdapter.conexionRestApi(gsonRelationship);
        Log.d("ACTI", action);
        Call<RelationshipResponse> relationshipResponseCall = endpointsApi.setRelationship(userID, action);
        relationshipResponseCall.enqueue(new Callback<RelationshipResponse>() {
            @Override
            public void onResponse(Call<RelationshipResponse> call, Response<RelationshipResponse> response) {
                Log.v("CODE_SEARCH", String.valueOf(response.code()));
                RelationshipResponse relationshipResponse = response.body();
                if(response.code()==200){
                    String funf = relationshipResponse.getOutgoing_status();
                    Log.d("FUNF",relationshipResponse.getOutgoing_status());
                    if(funf.equals(FOLLOWS)){
                        Toast.makeText(context, "Has seguido a " + usuario , Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Dejaste de seguir a " + usuario , Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(context, "Lo sentimos no se pudo realizar follow/unfollow", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RelationshipResponse> call, Throwable t) {
                Log.v("CODE", "-3000");
                Toast.makeText(context, "Problemas de conexión. Intenta nuevamente", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
