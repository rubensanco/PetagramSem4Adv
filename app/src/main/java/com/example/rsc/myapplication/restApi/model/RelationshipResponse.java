package com.example.rsc.myapplication.restApi.model;


public class RelationshipResponse {
    private String outgoing_status;
    private String target_user_is_private;
    private String incoming_status;
    private int code;

    public RelationshipResponse() {
    }

    public RelationshipResponse(String outgoing_status, String target_user_is_private, String incoming_status, int code) {
        this.outgoing_status = outgoing_status;
        this.target_user_is_private = target_user_is_private;
        this.incoming_status = incoming_status;
        this.code = code;
    }

    public String getOutgoing_status() {
        return outgoing_status;
    }

    public void setOutgoing_status(String outgoing_status) {
        this.outgoing_status = outgoing_status;
    }

    public String getTarget_user_is_private() {
        return target_user_is_private;
    }

    public void setTarget_user_is_private(String target_user_is_private) {
        this.target_user_is_private = target_user_is_private;
    }

    public String getIncoming_status() {
        return incoming_status;
    }

    public void setIncoming_status(String incoming_status) {
        this.incoming_status = incoming_status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
