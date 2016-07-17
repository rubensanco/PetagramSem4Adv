package com.example.rsc.myapplication.pojo;

import java.io.Serializable;


public class Mascota implements Serializable{

    private String id;
    private String nombre;
    private String imagen;
    private String idImagen;
    private int likes;

    public Mascota() {

    }

    public Mascota(String nombre, String imagen, int likes) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.likes = likes;
    }

    public Mascota(String id, String nombre, String imagen, int likes) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.likes = likes;
    }

    public Mascota(String id, String nombre, String imagen, String idImagen, int likes) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.idImagen = idImagen;
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void sumaLike () {
        this.likes = this.likes + 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(String idimagen) {
        this.idImagen = idimagen;
    }
}
