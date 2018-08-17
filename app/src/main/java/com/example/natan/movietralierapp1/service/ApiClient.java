package com.example.natan.movietralierapp1.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    final static String MOVIE_DB_URL = "http://api.themoviedb.org/3/";
    public static String api_key = "053130b8fdf68ca19c58155b4bd37bdd";
    private static Retrofit retrofit = null;

    public static ApiInterface getClient() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MOVIE_DB_URL);

        return builder.build().create(ApiInterface.class);
    }
}



