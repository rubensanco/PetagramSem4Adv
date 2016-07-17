package com.example.rsc.myapplication.restApi;

import com.example.rsc.myapplication.restApi.model.MascotaResponse;
import com.example.rsc.myapplication.restApi.model.MediaLikeResponse;
import com.example.rsc.myapplication.restApi.model.RelationshipResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface EndpointsApi {

    @GET(ConstantesRestApi.URL_GET_FOLLOWS)
    Call<MascotaResponse> getUserFollows();

    @GET(ConstantesRestApi.KEY_GET_RECENT_MEDIA_USERS + "{user}" + ConstantesRestApi.URL_RECENT_MEDIA)
    Call<MascotaResponse> getUserRecentMedia(@Path("user") String user, @Query("count") int count);

    @GET(ConstantesRestApi.KEY_GET_SEARCH +  ConstantesRestApi.URL_SEARCH)
    Call<MascotaResponse> getUserSearch(@Query("q") String q);

    @GET(ConstantesRestApi.KEY_GET_RECENT_MEDIA_USERS + "{user}" + ConstantesRestApi.URL_RECENT_MEDIA_ALL)
    Call<MascotaResponse> getUserRecentMediaAll(@Path("user") String user);

    @FormUrlEncoded
    @POST(ConstantesRestApi.KEY_SET_LIKE)
    Call<MediaLikeResponse> setLikeMedia (@Path("media-id") String id_foto_instagram,@Field("access_token") String access_token);

    @GET (ConstantesRestApi.URL_RELATIONSHIP)
    Call<RelationshipResponse> getRelationship (@Path("user-id") String user_id);

    @FormUrlEncoded
    @POST (ConstantesRestApi.URL_RELATIONSHIP)
    Call<RelationshipResponse> setRelationship (@Path("user-id") String user_id, @Field("action") String action);

}
