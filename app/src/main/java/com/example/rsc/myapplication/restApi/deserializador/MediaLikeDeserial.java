package com.example.rsc.myapplication.restApi.deserializador;

import com.example.rsc.myapplication.restApi.model.MediaLikeResponse;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;


public class MediaLikeDeserial implements JsonDeserializer<MediaLikeResponse> {
    @Override
    public MediaLikeResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        MediaLikeResponse mediaLikeResponse = gson.fromJson(json,MediaLikeResponse.class);
        JsonObject metaResponseData = json.getAsJsonObject().getAsJsonObject(JsonKeys.LIKE_META);
        int code = metaResponseData.get(JsonKeys.LIKE_CODE).getAsInt();
        mediaLikeResponse.setCode(code);
        mediaLikeResponse.setId_foto_instagram("");

        return mediaLikeResponse;
    }
}
