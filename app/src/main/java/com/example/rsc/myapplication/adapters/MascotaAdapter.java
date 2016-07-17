package com.example.rsc.myapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rsc.myapplication.R;
import com.example.rsc.myapplication.db.ConstructorMascotas;
import com.example.rsc.myapplication.pojo.Mascota;
import com.example.rsc.myapplication.restApi.ConstantesRestApi;
import com.example.rsc.myapplication.restApi.EndpointsApi;
import com.example.rsc.myapplication.restApi.adapter.RestApiAdapter;
import com.example.rsc.myapplication.restApi.model.MediaLikeResponse;
import com.example.rsc.myapplication.restApiHeroku.EndpointsHeroku;
import com.example.rsc.myapplication.restApiHeroku.adapter.RestApiHerokuAdapter;
import com.example.rsc.myapplication.restApiHeroku.model.MediaLikeNotifResponse;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private ArrayList<Mascota> mascotas;
    private Activity activity;
    private String id_usuario_instagram;
    private String id_dispositivo;

    public static class MascotaViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagen;
        public TextView nombre;
        public TextView numero;
        public ImageButton huesoLike;


        public MascotaViewHolder(View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.imagen_card);
            nombre = (TextView) itemView.findViewById(R.id.nombre_card);
            numero = (TextView) itemView.findViewById(R.id.numero_card);
            huesoLike = (ImageButton) itemView.findViewById(R.id.huesito_card);

        }
    }

    public MascotaAdapter(ArrayList<Mascota> mascotas, Activity activity) {
        this.mascotas = mascotas;
        this.activity = activity;
    }


    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mascota_card, parent, false);
        obtieneSharedpreferences();
        return new MascotaViewHolder(v);
    }

    private void obtieneSharedpreferences() {
        SharedPreferences miCuentaPref = activity.getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);
        id_dispositivo = miCuentaPref.getString("idDispositivo", null);
        id_usuario_instagram = miCuentaPref.getString("cuenta", null);

    }

    @Override
    public void onBindViewHolder(final MascotaViewHolder holder, final int i) {
//        holder.imagen.setImageResource(mascotas.get(i).getImagen());
        Picasso.with(activity)
                .load(mascotas.get(i).getImagen())
                .placeholder(R.drawable.dog_footprint_48)
                .into(holder.imagen);
        holder.nombre.setText(mascotas.get(i).getNombre());
        holder.numero.setText(String.valueOf(mascotas.get(i).getLikes()));
        holder.huesoLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RestApiAdapter restApiAdapter = new RestApiAdapter();
                Gson gsonMediaLike = restApiAdapter.gsonDeserialSetLikeMedia();
                EndpointsApi endpointsApi = restApiAdapter.conexionRestApi(gsonMediaLike);
                Call<MediaLikeResponse> mediaLikeResponseCall = endpointsApi.setLikeMedia(mascotas.get(i).getIdImagen(), ConstantesRestApi.ACCESS_TOKEN);
                mediaLikeResponseCall.enqueue(new Callback<MediaLikeResponse>() {
                    @Override
                    public void onResponse(Call<MediaLikeResponse> call, Response<MediaLikeResponse> response) {
                        MediaLikeResponse mediaLikeResponse = response.body();
                        if (mediaLikeResponse.getCode() == 200) {
                            mascotas.get(i).sumaLike();
                            if(id_dispositivo == null){
                                Toast.makeText(activity, "Se requiere activar la recepcion de notificaciones", Toast.LENGTH_LONG).show();

                            }else{
                                enviaNotificacionMediaLike(mascotas.get(i).getIdImagen(),mascotas.get(i).getNombre());

                            }

                            holder.numero.setText(String.valueOf(mascotas.get(i).getLikes()));
                            Toast.makeText(activity, "Diste Like", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Error intenta mas tarde", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MediaLikeResponse> call, Throwable t) {
                        Toast.makeText(activity, "Error intenta mas tarde", Toast.LENGTH_SHORT).show();
                    }
                });

                /*Snackbar.make(v, "Diste Like a " + mascotas.get(i).getNombre()  , Snackbar.LENGTH_LONG)
                        .setAction(" ", null).show();
                ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);
                constructorMascotas.darLikeMascota(mascotas.get(i));
                mascotas.get(i).sumaLike();
                holder.numero.setText(String.valueOf(constructorMascotas.obtenerLikesMascota(mascotas.get(i))));*/
            }
        });

    }

    public void enviaNotificacionMediaLike(String id_foto_instagram,String id_usuario_instagram) {
        Log.d("Inicio","enviaNotoficacionMediaLike");
        MediaLikeNotifResponse mediaLikeNotifResponse = new MediaLikeNotifResponse(id_foto_instagram,id_usuario_instagram,id_dispositivo);
        RestApiHerokuAdapter restApiHerokuAdapter = new RestApiHerokuAdapter();
        EndpointsHeroku endpointsHeroku = restApiHerokuAdapter.conexionRestAPIHeroku();
        Call<MediaLikeNotifResponse> mediaLikeNotifResponseCall = endpointsHeroku.enviaNotificacionLike(mediaLikeNotifResponse.getId_foto_instagram(),
                mediaLikeNotifResponse.getId_usuario_instagram(),mediaLikeNotifResponse.getId_dispositivo());
        mediaLikeNotifResponseCall.enqueue(new Callback<MediaLikeNotifResponse>() {
            @Override
            public void onResponse(Call<MediaLikeNotifResponse> call, Response<MediaLikeNotifResponse> response) {
                MediaLikeNotifResponse mediaLikeNotifResponse1 = response.body();
                Toast.makeText(activity, "Notificacion Enviada", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<MediaLikeNotifResponse> call, Throwable t) {
                Toast.makeText(activity, "Notificacion No Enviada", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }



}
