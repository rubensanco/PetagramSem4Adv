package com.example.rsc.myapplication.restApi.deserializador;

import android.util.Log;

import com.example.rsc.myapplication.pojo.Mascota;
import com.example.rsc.myapplication.restApi.model.MascotaResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MascotaDeserialUsersFollows implements JsonDeserializer <MascotaResponse> {

    @Override
    public MascotaResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        MascotaResponse mascotaResponse = gson.fromJson(json,MascotaResponse.class);
        JsonArray mascotaResponseData = json.getAsJsonObject().getAsJsonArray(JsonKeys.MEDIA_RESPONSE_ARRAY);
        mascotaResponse.setMascotas(deserializarUsuarios(mascotaResponseData));
        Log.v("MASSIZE", String.valueOf(mascotaResponse.getMascotas().size()));
        return mascotaResponse;
    }

    private ArrayList<Mascota> deserializarUsuarios(JsonArray mascotaResponseData) {

        ArrayList<Mascota> mascotas = new ArrayList<Mascota>();
        Log.v("SIZE", String.valueOf(mascotaResponseData.size()));
        for (int i = 0; i < mascotaResponseData.size() ; i++) {
            JsonObject mascotaResponseDataObject = mascotaResponseData.get(i).getAsJsonObject();
            String id = mascotaResponseDataObject.get(JsonKeys.USER_ID).getAsString();
            String profileImage = mascotaResponseDataObject.get(JsonKeys.USER_PROFILE_IMAGE).getAsString();
            String nombre = mascotaResponseDataObject.get(JsonKeys.USER_NOMBRE_COMPLETO).getAsString();
            Log.v("id", id);
            mascotas.add(new Mascota(id,nombre,profileImage,"",0));

        }
        return mascotas;
    }


}
