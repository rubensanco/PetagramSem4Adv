package com.example.rsc.myapplication.restApi.deserializador;


import com.example.rsc.myapplication.restApi.model.RelationshipResponse;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class RelationshipDeserial implements JsonDeserializer <RelationshipResponse> {

    @Override
    public RelationshipResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        RelationshipResponse relationshipResponse = gson.fromJson(json,RelationshipResponse.class);
        JsonObject metaResponseData = json.getAsJsonObject().getAsJsonObject(JsonKeys.RELATIONSHIP_META);
        int code = metaResponseData.get(JsonKeys.RELATIONSHIP_CODE).getAsInt();
        JsonObject dataResponseData = json.getAsJsonObject().getAsJsonObject(JsonKeys.RELATIONSHIP_DATA);
        String outStat = dataResponseData.get(JsonKeys.RELATIONSHIP_OUTSTAT).getAsString();
        String target = dataResponseData.get(JsonKeys.RELATIONSHIP_TARGET).getAsString();
        String inStat = "";
        //String inStat = dataResponseData.get(JsonKeys.RELATIONSHIP_INSTAT).getAsString();
        relationshipResponse.setCode(code);
        relationshipResponse.setIncoming_status(inStat);
        relationshipResponse.setOutgoing_status(outStat);
        relationshipResponse.setTarget_user_is_private(target);


        return relationshipResponse;
    }


}
