package com.example.rsc.myapplication.restApiHeroku.model;


public class UsuarioResponse {

    private String id;
    private String id_dispositivo;
    private String id_usuario_instagram;

    public UsuarioResponse() {
    }

    public UsuarioResponse(String id, String id_dispositivo, String id_usuario_instagram) {
        this.id = id;
        this.id_dispositivo = id_dispositivo;
        this.id_usuario_instagram = id_usuario_instagram;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdDispositivo() {
        return id_dispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.id_dispositivo = idDispositivo;
    }

    public String getIdUsuario() {
        return id_usuario_instagram;
    }

    public void setIdUsuario(String idUsuario) {
        this.id_usuario_instagram = idUsuario;
    }
}
