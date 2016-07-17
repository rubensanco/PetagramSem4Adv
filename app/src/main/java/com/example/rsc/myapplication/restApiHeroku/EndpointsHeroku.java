package com.example.rsc.myapplication.restApiHeroku;

import com.example.rsc.myapplication.restApiHeroku.model.MediaLikeNotifResponse;
import com.example.rsc.myapplication.restApiHeroku.model.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface EndpointsHeroku {

    @FormUrlEncoded
    @POST(ConstantesRestApiHeroku.KEY_POST_REG_USUARIO)
    Call<UsuarioResponse> registrarTokenID(@Field("id_dispositivo") String idDispo, @Field("id_usuario_instagram") String idUsuInstagram);

    @GET(ConstantesRestApiHeroku.KEY_GET_MASCOTA_LIKE)
    Call<MediaLikeNotifResponse> enviaNotificacionLike(@Path("id_foto_instagram") String id_foto_instagram,@Path("id_usuario_instagram") String id_usuario_instagram,@Path("id_dispositivo") String id_dispositivo);

}
