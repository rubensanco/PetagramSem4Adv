package com.example.rsc.myapplication.restApi;


public class ConstantesRestApi {

    //URL BASE
    public static final String VERSION = "/v1/";
    public static final String ROOT_URL = "https://api.instagram.com" + VERSION;
    public static final String ACCESS_TOKEN = "3437117661.490cc24.97905862da044029a65f8436897499c5";
    public static final String KEY_ACCESS_TOKEN = "?access_token=";
    public static final String KEY_COUNT = "&count=3";
    public static final String KEY_SCOPE = "&scope=public_content";
    //para perfil mascota
    public static final String KEY_GET_RECENT_MEDIA_USERS = "users/";
    public static final String KEY_GET_RECENT_MEDIA = "/media/recent/";
    public static final String URL_RECENT_MEDIA = KEY_GET_RECENT_MEDIA + KEY_ACCESS_TOKEN + ACCESS_TOKEN + KEY_SCOPE + KEY_COUNT;
    //para timeline
    //los usuarios que sigo
    public static final String KEY_GET_FOLLOWS = "users/self/follows";
    public static final String URL_GET_FOLLOWS = KEY_GET_FOLLOWS + KEY_ACCESS_TOKEN + ACCESS_TOKEN;

    //para buscar el usuario por nombre
    public static final String KEY_GET_SEARCH = "users/search";
    public static final String KEY_GET_Q_PARM = "?q=";
    public static final String KEY_ACCESS_TOKEN_SEARCH = "?access_token=";
    public static final String URL_SEARCH = KEY_ACCESS_TOKEN_SEARCH + ACCESS_TOKEN + KEY_SCOPE;

    public static final String URL_RECENT_MEDIA_ALL = KEY_GET_RECENT_MEDIA + KEY_ACCESS_TOKEN + ACCESS_TOKEN + KEY_SCOPE;

    //para dar like a foto
    public static final String KEY_SET_LIKE = "media/{media-id}/likes";

    //para get y post de relationships
    public static final String KEY_RELATIONSHIP = "users/{user-id}/relationship";
    public static final String KEY_ACCESS_TOKEN_RELATIONSHIP = "?access_token=";
    public static final String URL_RELATIONSHIP = KEY_RELATIONSHIP + KEY_ACCESS_TOKEN_RELATIONSHIP + ACCESS_TOKEN;





}
